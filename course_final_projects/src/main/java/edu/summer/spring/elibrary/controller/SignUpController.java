package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.model.Reader;
import edu.summer.spring.elibrary.model.Role;
import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.ReaderRepository;
import edu.summer.spring.elibrary.repository.SubscriptionRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("signup")
public class SignUpController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    @Qualifier("bcryptPasswordEncoder")
    private PasswordEncoder encoder;

    @GetMapping
    public String   signUp() {
        return "signup";
    }

//    TODO: Fix bug with sign up (doesn't get called when button is pressed)
    @PostMapping
    public String   addUser(User user, Model model) {
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            model.addAttribute("message", "User exists");
            return "signup";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRole(Role.READER);
        userRepository.save(user);
        Subscription subscription = new Subscription(user, UUID.randomUUID().toString());
        Reader reader = new Reader(user, subscription);
        subscriptionRepository.save(subscription);
        readerRepository.save(reader);
        return "/";
    }

}
