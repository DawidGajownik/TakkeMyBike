package pl.coderslab.rent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bike.Bike;
import pl.coderslab.bike.BikeService;
import pl.coderslab.rating.Rating;
import pl.coderslab.rating.RatingService;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/rent")
@RequiredArgsConstructor

public class RentController {

    private final RentService rentService;
    private final BikeService bikeService;
    private final UserService userService;
    private final RatingService ratingService;

    @GetMapping("/bike/{bikeId}")
    public String showRentForm(@PathVariable Long bikeId, Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        List<Rent> rents = rentService.getRentsForBikeId(bikeId);
        Optional<Bike> bikeOptional = bikeService.findById(bikeId);
        if (bikeOptional.isEmpty()) {
            model.addAttribute("error", "Rower nie został znaleziony");
            return "redirect:/bike";
        }
        if(session.getAttribute("id").toString().equals(bikeOptional.get().getOwner().getId().toString())) {
            return "redirect:/bike/details/"+bikeId;
        }
        List<LocalDate> disabledDates = rentService.getDisabledDatesForBike(bikeId);
        userService.refreshNotifications(session);
        model.addAttribute("rents", rents);
        model.addAttribute("disabledDates", disabledDates);
        model.addAttribute("bike", bikeOptional.get());
        model.addAttribute("rent", new Rent());
        return "Rent";
    }

    @PostMapping
    public String rentBike(@ModelAttribute Rent rent, HttpSession session, Model model, HttpServletRequest request) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Optional<Bike> bikeOptional = bikeService.findById(Long.valueOf(request.getParameter("bikeId")));
        if (bikeOptional.isEmpty()) {
            model.addAttribute("error", "Rower nie został znaleziony");
            return "redirect:/bike";
        }
        StringBuilder sb = new StringBuilder();
        List<LocalDate> disabledDates = rentService.getDisabledDatesForBike(bikeOptional.get().getId()).stream().sorted().toList();
        boolean rentPossible = true;
        for (int i = 0; i < disabledDates.size(); i++) {
            if (isBetween(disabledDates.get(i), rent.getStartDate(), rent.getEndDate())){
                sb.append(disabledDates.get(i)).append("<br>");
                rentPossible = false;
            }
        }
        int duration = Period.between(rent.getStartDate(), rent.getEndDate()).getDays()+1;
        Bike bike = bikeOptional.get();
        if (!rentPossible || duration<bike.getMinRentDays()){
            StringBuilder error = new StringBuilder();
            if (!rentPossible) {
                error.append("Podane daty są zajęte:<br>".concat(String.valueOf(sb)));
                session.setAttribute("error", error);
            }
            if (duration<bike.getMinRentDays()) {
                error.append("Minimalny czas wynajmu wynosi ").append(bike.getMinRentDays()).append(" dni");
                session.setAttribute("error", error);
            }
            return "redirect:/rent/bike/"+bikeOptional.get().getId();
        }
        User owner = bike.getOwner();
        rent.setOwner(owner);
        rent.setStatus(0);
        rent.setDuration(duration);
        Optional<User> userOptional = userService.findById(Long.valueOf(session.getAttribute("id").toString()));
        rent.setUser(userOptional.get());
        rent.setBike(bike);
        owner.setHasRentNotifications(true);
        userService.save(owner);
        rentService.rentBike(rent);
        session.setAttribute("msg", "Wniosek o wypożyczenie został wysłany. Czekaj na potwierdzenie wynajmującego.");
        return "redirect:/rent/bike/"+bikeOptional.get().getId();
    }
    @GetMapping
    public String getRequests (Model model, HttpSession session) {
        if (session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        Long loggedUserId = Long.valueOf(session.getAttribute("id").toString());
        Optional <User> userOptional = userService.findById(loggedUserId);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOptional.get();
        Map <Rent, Rating> rentRatingMap = rentService.rentsWithMyRatings(rentService.myRents(loggedUserId), loggedUserId);
        user.setHasReservationNotifications(false);
        session.setAttribute("hasReservationNotifications", user.isHasReservationNotifications());
        userService.save(user);
        userService.refreshNotifications(session);
        model.addAttribute("ratings",rentRatingMap);
        model.addAttribute("now", LocalDate.now());
        model.addAttribute("reservations", rentRatingMap);
        return "Reservations";
    }
    @GetMapping("/my-dashboard")
    public String ownerDashboard (Model model, HttpSession session) {
        if (session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        Long loggedUserId = Long.valueOf(session.getAttribute("id").toString());
        Optional <User> userOptional = userService.findById(loggedUserId);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOptional.get();
        user.setHasRentNotifications(false);
        session.setAttribute("hasRentNotifications", user.isHasRentNotifications());
        userService.save(user);
        userService.refreshNotifications(session);
        var myOwns = rentService.myOwns(loggedUserId).stream().sorted((s1,s2) -> s2.getId().compareTo(s1.getId())).toList();
        Map <Rent, Rating> rentRatingMap = rentService.rentsWithMyRatings(myOwns, loggedUserId);
        model.addAttribute("now", LocalDate.now());
        model.addAttribute("mydashboard", true);
        model.addAttribute("reservations", rentRatingMap);
        return "Reservations";
    }
    @PostMapping("/cancel/{id}")
    public String cancel (@PathVariable Long id, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Rent rent = rentService.get(id);
        if (!session.getAttribute("id").toString().equals(rent.getUser().getId().toString())){
            return "redirect:/";
        }
        User owner = rent.getOwner();
        owner.setHasRentNotifications(true);
        userService.save(owner);
        rent.setStatus(3);
        rentService.update(rent);
        userService.refreshNotifications(session);
        return "redirect:/rent";
    }
    @PostMapping("/accept/{id}")
    public String accept (@PathVariable Long id, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Rent rentProcessed = rentService.get(id);
        if (!session.getAttribute("id").toString().equals(rentProcessed.getOwner().getId().toString())){
            return "redirect:/";
        }
        List <LocalDate> datesProcessed = rentService.getDatesFromRent(rentProcessed);
        List <Rent> rentList = rentService.getRentsForBikeId(rentProcessed.getBike().getId());
        for (int i = 0; i < rentList.size(); i++) {
            Rent rent = rentList.get(i);
            List <LocalDate> dates = rentService.getDatesFromRent(rentList.get(i));
            for (int j = 0; j < dates.size(); j++) {
                for (int k = 0; k < datesProcessed.size(); k++) {
                    if (dates.get(j).isEqual(datesProcessed.get(k))){
                        rent.setStatus(1);
                        rentService.update(rent);
                    }
                }
            }
        }
        User user = rentProcessed.getUser();
        user.setHasReservationNotifications(true);
        userService.save(user);
        rentProcessed.setStatus(2);
        rentService.update(rentProcessed);
        userService.refreshNotifications(session);
        return "redirect:/rent/my-dashboard";
    }
    @PostMapping("/deny/{id}")
    public String deny (@PathVariable Long id, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Rent rent = rentService.get(id);
        if (!session.getAttribute("id").toString().equals(rent.getOwner().getId().toString())){
            return "redirect:/";
        }
        rent.setStatus(1);
        User user = rent.getUser();
        user.setHasReservationNotifications(true);
        userService.save(user);
        rentService.update(rent);
        userService.refreshNotifications(session);
        return "redirect:/rent/my-dashboard";
    }
    public boolean isBetween (LocalDate day, LocalDate start, LocalDate end) {
        boolean result = false;
        if (day.isEqual(start)||day.isEqual(end)||(day.isAfter(start)&&day.isBefore(end))){
            result = true;
        }
        return result;
    }
}
