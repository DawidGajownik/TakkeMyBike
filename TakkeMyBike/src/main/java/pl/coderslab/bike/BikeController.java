package pl.coderslab.bike;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.address.Address;
import pl.coderslab.image.ImageService;
import pl.coderslab.rent.Rent;
import pl.coderslab.rent.RentService;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/bike")
@RequiredArgsConstructor

public class BikeController {

    private final BikeService bikeService;
    private final UserService userService;
    private final RentService rentService;
    private final ImageService imageService;



    @GetMapping("/add")
    public String add (Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        if (session.getAttribute("rentToOthers").toString().equals("false")) {
            return "redirect:/login";
        }
        userService.refreshNotifications(session);
        model.addAttribute("bike", new Bike());
        return "AddBike";
    }

    @GetMapping("/manage/{id}")
    public String update (Model model, @PathVariable Long id, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
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
        userService.refreshNotifications(session);
        model.addAttribute("bike", bikeOptional.get());
        return "AddBike";
    }

    @GetMapping("/asdasd")
    public String showBikeList(HttpSession session,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) Integer minPrice,
                               @RequestParam(required = false) Integer maxPrice,
                               @RequestParam(required = false) String owner,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) Integer maxDistance,
                               @RequestParam(required = false) Integer minRentDays,
                               @RequestParam(required = false) String startDate,
                               @RequestParam(required = false) String endDate,
                               @RequestParam(required = false) String sort,
                               Model model) {

//        List<Bike> bikes = bikeService.findBikesWithFilters(search, minPrice, maxPrice, owner, address,
//                maxDistance, minRentDays, startDate, endDate, sort);

//        model.addAttribute("bikes", bikes);


        model.addAttribute("rents", rentService.all());


        model.addAttribute("id", Long.valueOf(session.getAttribute("id").toString())); // Dodajemy id użytkownika z sesji

        return "bike/list"; // Zwracamy nazwę widoku JSP
    }

    @PostMapping("/save")
    public String save (@ModelAttribute Bike bike, HttpSession session) throws IOException {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        bike.setOwner(userService.findById(Long.valueOf(session.getAttribute("id").toString())).get());
        bike.setBase64Image(Base64.getEncoder().encodeToString(bike.getImage()));
        bikeService.save(bike);
        imageService.setImages(bikeService.processImages(bike));
        return "redirect:/";
    }
    @GetMapping("/user/{id}")
    public String userBikes(@PathVariable Long id, Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Optional <User> userOptional = userService.findById(id);
        model.addAttribute("PageStatus","Rowery użytkownika "+ userOptional.get().getLogin());
        return getUserBikes(model, userOptional, session);
    }

    @GetMapping("/mine")
    public String mine (Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        userService.refreshNotifications(session);
        if (session.getAttribute("rentToOthers").toString().equals("false")) {
            return "redirect:/login";
        }
        Optional <User> userOptional = userService.findById(Long.valueOf(session.getAttribute("id").toString()));
        model.addAttribute("PageStatus","Moje rowery");
        return getUserBikes(model, userOptional, session);
    }

    @GetMapping
    public String all (Model model,
                       HttpSession session,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Double minPrice,
                       @RequestParam(required = false) Double maxPrice,
                       @RequestParam(required = false) String owner,
                       @RequestParam(required = false) String address,
                       @RequestParam(required = false) Integer maxDistance,
                       @RequestParam(required = false) Integer minRentDays,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(required = false) String sort) throws IOException {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        userService.refreshNotifications(session);
        model.addAttribute("PageStatus","Wszystkie rowery");
        Map <Bike, Double> bikes = bikeService.findBikesWithFilters(search, minPrice, maxPrice, owner, address,
                maxDistance, minRentDays, startDate, endDate, sort);
        model.addAttribute("rents", rentService.all());
        model.addAttribute("bike", bikes);
        return "Bikes";
    }

    private String getUserBikes(Model model, Optional<User> userOptional, HttpSession session) {
        if (userOptional.isEmpty()){
            return "redirect:/bike";
        }
        userService.refreshNotifications(session);
        //model.addAttribute("rentedBikesIds", rentService.all().stream().map(Rent::getId));
        model.addAttribute("rents", rentService.all());
        model.addAttribute("bike", bikeService.findAllByOwner(userOptional.get()));
        return "Bikes";
    }

    @GetMapping("/details/{bikeId}")
    private String details (HttpSession session, Model model, @PathVariable Long bikeId) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Optional<Bike> bikeOptional = bikeService.findById(bikeId);
        if (bikeOptional.isEmpty()) {
            model.addAttribute("error", "Rower nie został znaleziony");
            return "redirect:/bike";
        }
        userService.refreshNotifications(session);
        model.addAttribute("bike", bikeOptional.get());
        return "BikeDetails";
    }
}
