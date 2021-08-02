package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.User;
import edu.summer.spring.elibrary.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LogInController {
    @Autowired
    private UserRepository  userRepository;

    @GetMapping
    public String   logIn() {
        return "login";
    }

    @PostMapping
    public String   loggingIn(User user, Model model) {
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb == null) {
            model.addAttribute("message", "User doesn't exist");
            return "login";
        } else {
            return "/";
        }
    }
}
