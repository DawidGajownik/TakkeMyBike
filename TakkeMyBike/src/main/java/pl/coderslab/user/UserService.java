package pl.coderslab.user;

import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void save (User user) {
        userRepository.save(user);
    }
    public Optional<User> findById (Long id) {
        return userRepository.findById(id);
    }

    public void refreshNotifications (HttpSession session) {
        Long id = Long.valueOf(session.getAttribute("id").toString());
        User loggedUser = findById(id).get();
        session.setAttribute("hasMessageNotification", loggedUser.isHasMessageNotification());
        session.setAttribute("hasRentNotifications", loggedUser.isHasRentNotifications());
        session.setAttribute("hasReservationNotifications", loggedUser.isHasReservationNotifications());
    }
    public boolean isUserLogged(HttpSession session) {
        if (session.getAttribute("id") == null) {
            return false;
        }
        Long loggedUserId = Long.valueOf(session.getAttribute("id").toString());
        Optional<User> userOptional = findById(loggedUserId);
        if (userOptional.isEmpty()) {
            return false;
        }
        return true;
    }
}
