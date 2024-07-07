package pl.coderslab.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.message.MessageService;
import pl.coderslab.rating.RatingService;
import pl.coderslab.utils.BCrypt;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final RatingService ratingService;
    private final MessageService messageService;

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
    public String save(@ModelAttribute User user, HttpSession session,
                       Model model,
                       @RequestParam(required = false) String oldPassword,
                       @RequestParam(required = false) String newPassword,
                       @RequestParam(required = false) String confirmPassword){
        Long loggedUserId = Long.valueOf(session.getAttribute("id").toString());
        String oldHashedPassword = userService.findById(loggedUserId).get().getPassword();
        if (oldPassword == null) {
            if (!user.getPassword().equals(confirmPassword)){
                session.setAttribute("wrngpsw", "Hasła się nie zgadzają");
                model.addAttribute("user", user);
                return "redirect:/register/";
            }
            user.setLevel(0);
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        } else {
            if (!BCrypt.checkpw(oldPassword, oldHashedPassword)&&!newPassword.equals(confirmPassword)){
                session.setAttribute("wrngpsw", "Błędne hasło oraz nowe hasła się nie zgadzają");
                model.addAttribute("user", user);
                return "redirect:/edit-profile/"+user.getId();
            } else if(!BCrypt.checkpw(oldPassword, oldHashedPassword)){
                session.setAttribute("wrngpsw", "Błędne hasło");
                model.addAttribute("user", user);
                return "redirect:/edit-profile/"+user.getId();
            } else if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("wrngpsw", "Hasła się nie zgadzają");
                model.addAttribute("user", user);
                return "redirect:/edit-profile/"+user.getId();
            } else {
                user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            }
        }
        userService.save(user);
        messageService.decryptAndEncryptAgainWithNewPassword(user.getId(), oldHashedPassword);
        Optional <User> loggedUserOpt = userService.findByEmail(user.getEmail());
        if (loggedUserOpt.isEmpty()) {
            return "redirect:/login";
        }
        User loggedUser = loggedUserOpt.get();
        addUserToSession(session, loggedUser);
        userService.refreshNotifications(session);
        return "redirect:/";
    }

    @GetMapping("/user/{userId}")
    public String getUserProfile(@PathVariable Long userId, Model model, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Optional <User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("avgRating", String.format("%.2f", ratingService.myRatings(userId).stream().mapToDouble(s -> (double) s.getRating()).average().orElse(0.0)));
        model.addAttribute("ratings", ratingService.myRatings(userId));
        userService.refreshNotifications(session);
        model.addAttribute(userOptional.get());
        return "Profile";
    }

    @GetMapping("/edit-profile/{userId}")
    public String editProfile (Model model, @PathVariable Long userId, HttpSession session) {
        if (!userService.isUserLogged(session)) return "redirect:/login";
        Long loggedUserId = Long.valueOf(session.getAttribute("id").toString());
        if (!userId.equals(loggedUserId)){
            return "redirect:/edit-profile/"+loggedUserId;
        }
        model.addAttribute("edit", true);
        model.addAttribute("user", userService.findById(userId));
        return "Register";
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
