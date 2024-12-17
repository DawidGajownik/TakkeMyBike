package pl.coderslab;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.utils.BCrypt;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        String haslo = "haslo";
        return BCrypt.hashpw(haslo, BCrypt.gensalt());
    }
}