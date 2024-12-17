package pl.coderslab.rating;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rent.Rent;
import pl.coderslab.rent.RentService;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/rate")
@RequiredArgsConstructor
public class RatingController {
    private final RentService rentService;
    private final UserService userService;
    private final RatingService ratingService;
    @GetMapping("/{id}")
    public String rate(HttpSession session, Model model, @PathVariable Long id){
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Long raterId = Long.valueOf(session.getAttribute("id").toString());
        Rent rent = rentService.get(id);
        LocalDate now = LocalDate.now();
        if (!(rent.getStatus()==2&&rent.getEndDate().isBefore(now)) ||
                !(rent.getUser().getId().equals(raterId)||rent.getOwner().getId().equals(raterId)) ||
                ratingService.getByRentIdAndRaterId(id, Long.valueOf(session.getAttribute("id").toString())).isPresent())
        {
            return "redirect:/";
        }
        Rating rating = new Rating();
        if (raterId.equals(rent.getOwner().getId())) {
            rating.setRated(rent.getUser());
            rating.setRater(rent.getOwner());
        } else {
            rating.setRated(rent.getOwner());
            rating.setRater(rent.getUser());
        }
        rating.setRent(rent);
        model.addAttribute("rating", rating);
        return "Rate";
    }
    @PostMapping
    public String addRate(@ModelAttribute Rating rating, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        rating.setDate(LocalDate.now());
        ratingService.addRating(rating);
        if (rating.getRated().getId().equals(ratingService.getById(rating.getId()).getRent().getOwner().getId())) {
            return "redirect:/rent";
        }
        return "redirect:/rent/my-dashboard";
    }
}
