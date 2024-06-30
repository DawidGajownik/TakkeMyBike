package pl.coderslab.user;

import org.springframework.stereotype.Service;

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
}
