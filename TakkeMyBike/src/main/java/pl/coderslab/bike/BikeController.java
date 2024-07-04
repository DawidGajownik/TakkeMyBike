package pl.coderslab.bike;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.rent.Rent;
import pl.coderslab.rent.RentService;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/bike")
@RequiredArgsConstructor

public class BikeController {

    private final BikeService bikeService;
    private final UserService userService;
    private final RentService rentService;
    private final BikeRepository bikeRepository;


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

    @PostMapping("/save")
    public String save (@ModelAttribute Bike bike, HttpSession session) throws IOException {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        //bike.setBase64Image(Base64.getEncoder().encodeToString(bike.getImage()));
        bike.setOwner(userService.findById(Long.valueOf(session.getAttribute("id").toString())).get());
        bikeService.save(bike);
        return "redirect:/";
    }
    @GetMapping("/user/{id}")
    public String userBikes(@PathVariable Long id, Model model, HttpSession session) {
        Optional <User> userOptional = userService.findById(id);
        return getUserBikes(model, userOptional, session);
    }

    @GetMapping("/mine")
    public String mine (Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        if (session.getAttribute("rentToOthers").toString().equals("false")) {
            return "redirect:/login";
        }
        Optional <User> userOptional = userService.findById(Long.valueOf(session.getAttribute("id").toString()));
        return getUserBikes(model, userOptional, session);
    }

    @GetMapping
    public String all (Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        userService.refreshNotifications(session);
        model.addAttribute("rentedBikesIds", rentService.all().stream().map(Rent::getId));
        model.addAttribute("rents", rentService.all());
        model.addAttribute("bike", bikeService.findAll());
        return "Bikes";
    }

    private String getUserBikes(Model model, Optional<User> userOptional, HttpSession session) {
        if (userOptional.isEmpty()){
            return "redirect:/bike";
        }
        userService.refreshNotifications(session);
        model.addAttribute("rentedBikesIds", rentService.all().stream().map(Rent::getId));
        model.addAttribute("rents", rentService.all());
        model.addAttribute("bike", bikeService.findAllByOwner(userOptional.get()));
        return "Bikes";
    }

}
