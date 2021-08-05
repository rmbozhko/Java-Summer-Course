package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.Reader;
import edu.summer.spring.elibrary.entity.Role;
import edu.summer.spring.elibrary.entity.Subscription;
import edu.summer.spring.elibrary.entity.User;
import edu.summer.spring.elibrary.repos.ReaderRepository;
import edu.summer.spring.elibrary.repos.SubscriptionRepository;
import edu.summer.spring.elibrary.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/signup")
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
        return "redirect:/login";
    }

}
