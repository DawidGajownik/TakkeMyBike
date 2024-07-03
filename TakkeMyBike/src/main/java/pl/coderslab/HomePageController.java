package pl.coderslab;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.bike.Bike;
import pl.coderslab.bike.BikeService;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.file.Path;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class HomePageController {

    private final BikeService bikeService;
    private final UserService userService;

    @GetMapping()
    public String HomePage (Model model, Bike bike, HttpSession session) {
        if (session.getAttribute("id")!=null) {
            userService.refreshNotifications(session);
        }
        model.addAttribute("bike", bikeService.findAll());
        return "HomePage";
    }
    @GetMapping("/logout")
    public String logout (HttpSession session) {
        session.removeAttribute("id");
        session.removeAttribute("rentToOthers");
        session.removeAttribute("rentFromOthers");
        session.removeAttribute("message");
        session.removeAttribute("error");
        return "redirect:/";
    }
}
