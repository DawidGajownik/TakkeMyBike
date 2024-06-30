package pl.coderslab.bike;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rent.Rent;
import pl.coderslab.rent.RentService;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/bike")
@RequiredArgsConstructor

public class BikeController {

    private final BikeService bikeService;
    private final UserService userService;
    private final RentService rentService;

    @GetMapping("/add")
    public String add (Model model, HttpSession session) {
        if (session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        if (session.getAttribute("rentToOthers").toString().equals("false")) {
            return "redirect:/login";
        }
        model.addAttribute("bike", new Bike());
        return "AddBike";
    }

    @GetMapping("/manage/{id}")
    public String update (Model model, @PathVariable Long id, HttpSession session) {
        Optional <Bike> bikeOptional = bikeService.findById(id);
        if (session.getAttribute("id") == null) {
            return "redirect:/bike/"+id;
        }
        if (session.getAttribute("rentToOthers").toString().equals("false")) {
            return "redirect:/bike/"+id;
        }
        if (bikeOptional.isEmpty()) {
            return "redirect:/bike/"+id;
        }
        model.addAttribute("bike", bikeOptional.get());
        return "AddBike";
    }

    @PostMapping("/save")
    public String save (@ModelAttribute Bike bike, HttpSession session, Model model) {
        Long id = Long.valueOf(session.getAttribute("id").toString());
        Optional <User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()){
            model.addAttribute("error", "błąd, nie znaleziono użytkownika");
            return "redirect:/bike/add";
        }
        bike.setOwner(userOptional.get());
        bikeService.save(bike);
        return "redirect:/";
    }
    @GetMapping("/user/{id}")
    public String userBikes(@PathVariable Long id, Model model) {
        Optional <User> userOptional = userService.findById(id);
        return getUserBikes(model, userOptional);
    }

    @GetMapping("/mine")
    public String mine (Model model, HttpSession session) {
        if (session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        if (session.getAttribute("rentToOthers").toString().equals("false")) {
            return "redirect:/login";
        }
        Optional <User> userOptional = userService.findById(Long.valueOf(session.getAttribute("id").toString()));
        return getUserBikes(model, userOptional);
    }

    @GetMapping
    public String all (Model model) {
        model.addAttribute("rentedBikesIds", rentService.all().stream().map(Rent::getId));
        model.addAttribute("rents", rentService.all());
        model.addAttribute("bike", bikeService.findAll());
        return "Bikes";
    }

    private String getUserBikes(Model model, Optional<User> userOptional) {
        if (userOptional.isEmpty()){
            return "redirect:/bike";
        }
        model.addAttribute("rentedBikesIds", rentService.all().stream().map(Rent::getId));
        model.addAttribute("rents", rentService.all());
        model.addAttribute("bike", bikeService.findAllByOwner(userOptional.get()));
        return "Bikes";
    }

}
