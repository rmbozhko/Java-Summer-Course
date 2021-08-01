package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.Role;
import edu.summer.spring.elibrary.entity.User;
import edu.summer.spring.elibrary.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String   signUp() {
        return "signup";
    }

    @PostMapping
    public String   addUser(User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User exists");
            return "signup";
        }
        user.setActive(true);
        user.setRole(Collections.singleton(Role.READER));
        userRepository.save(user);
        return "redirect:/login";
    }

}
