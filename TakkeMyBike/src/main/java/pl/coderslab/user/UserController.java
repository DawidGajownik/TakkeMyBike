package pl.coderslab.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rating.RatingService;
import pl.coderslab.utils.BCrypt;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final RatingService ratingService;

    @GetMapping("/login")
    public String loginGet(Model model){
        model.addAttribute("user", new User());
        return "Login";
    }

    @GetMapping("/register")
    public String registerGet(Model model){
        model.addAttribute("user", new User());
        return "Register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user, HttpSession session){
        user.setLevel(0);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.save(user);
        Optional <User> loggedUserOpt = userService.findByEmail(user.getEmail());
        if (loggedUserOpt.isEmpty()) {
            return "redirect:/login";
        }
        User loggedUser = loggedUserOpt.get();
        addUserToSession(session, loggedUser);
        userService.refreshNotifications(session);
        return "redirect:/";
    }

    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable Long id, Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Optional <User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("avgRating", String.format("%.2f", ratingService.myRatings(id).stream().mapToDouble(s -> (double) s.getRating()).average().orElse(0.0)));
        model.addAttribute("ratings", ratingService.myRatings(id));
        userService.refreshNotifications(session);
        model.addAttribute(userOptional.get());
        return "Profile";

    }
    @PostMapping("/login")
    public String loginPost(@ModelAttribute User user, Model model, HttpSession session){
        Optional <User> userOptional = userService.findByEmail(user.getEmail());
        if(userOptional.isEmpty()){
            model.addAttribute("msg", "Nie znaleziono użytkownika o podanym emailu");
            model.addAttribute("user", new User());
            return "Login";
        }
        User loggedUser = userOptional.get();

        if(!BCrypt.checkpw(user.getPassword(),loggedUser.getPassword())){
            String email = user.getEmail();
            User user1 = new User();
            user1.setEmail(email);
            model.addAttribute("msgpsw", "Błędne hasło");
            model.addAttribute("user", user1);
            return "Login";
        }
        addUserToSession(session, loggedUser);
        userService.refreshNotifications(session);

        return "redirect:/";
    }
    private void addUserToSession (HttpSession session, User loggedUser) {
        session.setAttribute("rentFromOthers", loggedUser.isRentFromOthers());
        session.setAttribute("rentToOthers", loggedUser.isRentToOthers());
        session.setAttribute("id", loggedUser.getId());
    }
}
