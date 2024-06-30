package pl.coderslab.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.utils.BCrypt;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "Login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "Register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute User user){
        user.setLevel(0);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        Optional <User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute(userOptional.get());
        return "Profile";

    }
    @PostMapping("/login")
    public String addUserToSession(@ModelAttribute User user, Model model, HttpSession session){
        Optional <User> userOptional = userService.findByEmail(user.getEmail());
        if(userOptional.isEmpty()){
            model.addAttribute("msg", "Nie znaleziono użytkownika o podanym emailu");
            model.addAttribute("user", new User());
            return "Login";
        }
        if(!BCrypt.checkpw(user.getPassword(),userOptional.get().getPassword())){
            String email = user.getEmail();
            User user1 = new User();
            user1.setEmail(email);
            model.addAttribute("msgpsw", "Błędne hasło");
            model.addAttribute("user", user1);
            return "Login";
        }
        User loggedUser = userOptional.get();
        session.setAttribute("hasMessageNotification", loggedUser.isHasMessageNotification());
        session.setAttribute("hasRentNotifications", loggedUser.isHasRentNotifications());
        session.setAttribute("hasReservationNotifications", loggedUser.isHasReservationNotifications());
        session.setAttribute("rentFromOthers", userOptional.get().isRentFromOthers());
        session.setAttribute("rentToOthers", userOptional.get().isRentToOthers());
        session.setAttribute("id", userOptional.get().getId());
        return "redirect:/";
    }
}
