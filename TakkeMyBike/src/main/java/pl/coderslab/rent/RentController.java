package pl.coderslab.rent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bike.Bike;
import pl.coderslab.bike.BikeService;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/rent")
@RequiredArgsConstructor

public class RentController {

    private final RentService rentService;
    private final BikeService bikeService;
    private final UserService userService;

    @GetMapping("/bike/{id}")
    public String showRentForm(@PathVariable Long id, Model model, HttpSession session) {
        List<Rent> rents = rentService.getRentsForBikeId(id);
        Long userId = (Long) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Bike> bikeOptional = bikeService.findById(id);
        if (bikeOptional.isEmpty()) {
            model.addAttribute("error", "Rower nie został znaleziony");
            return "redirect:/bike";
        }
        List<LocalDate> disabledDates = rentService.getDisabledDatesForBike(id);
        model.addAttribute("rents", rents);
        model.addAttribute("disabledDates", disabledDates);
        model.addAttribute("bike", bikeOptional.get());
        model.addAttribute("rent", new Rent());
        return "Rent";
    }

    @PostMapping
    public String rentBike(@ModelAttribute Rent rent, HttpSession session, Model model, HttpServletRequest request) {
        if (session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        Optional<Bike> bikeOptional = bikeService.findById(Long.valueOf(request.getParameter("bikeId")));
        if (bikeOptional.isEmpty()) {
            model.addAttribute("error", "Rower nie został znaleziony");
            return "redirect:/bike";
        }
        Optional <User> userOptional = userService.findById(Long.valueOf(session.getAttribute("id").toString()));
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Uzytkownik nie został odnaleziony");
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
        Long id = Long.valueOf(session.getAttribute("id").toString());
        Optional <User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOptional.get();
        user.setHasReservationNotifications(false);
        session.setAttribute("hasReservationNotifications", user.isHasReservationNotifications());
        userService.save(user);
        model.addAttribute("owner", false);
        model.addAttribute("reservations", rentService.myRents(id));
        return "Reservations";
    }
    @GetMapping("/my-dashboard")
    public String ownerDashboard (Model model, HttpSession session) {
        Long id = Long.valueOf(session.getAttribute("id").toString());
        Optional <User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOptional.get();
        user.setHasRentNotifications(false);
        session.setAttribute("hasRentNotifications", user.isHasRentNotifications());
        userService.save(user);
        model.addAttribute("owner", true);
        model.addAttribute("reservations", rentService.myOwns(id));
        return "Reservations";
    }
    @PostMapping("/cancel/{id}")
    public String cancel (@PathVariable Long id) {
        Rent rent = rentService.get(id);
        User owner = rent.getOwner();
        owner.setHasRentNotifications(true);
        userService.save(owner);
        rent.setStatus(3);
        rentService.update(rent);
        return "redirect:/rent";
    }
    @PostMapping("/accept/{id}")
    public String accept (@PathVariable Long id) {
        Rent rentProcessed = rentService.get(id);
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
        return "redirect:/rent/my-dashboard";
    }
    @PostMapping("/deny/{id}")
    public String deny (@PathVariable Long id) {
        Rent rent = rentService.get(id);
        rent.setStatus(1);
        User user = rent.getUser();
        user.setHasReservationNotifications(true);
        userService.save(user);
        rentService.update(rent);
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
