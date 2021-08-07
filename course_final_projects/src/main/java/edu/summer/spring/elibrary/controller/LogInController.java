package edu.summer.spring.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LogInController {

    @GetMapping
    public String   logIn(@RequestParam(name = "error", required = false) String error,
                          @RequestParam(name = "logout", required = false) String logout,
                          Model model) {
        if (error != null) {
            model.addAttribute("error", "Bad credentials.");
        } else if (logout != null) {
            model.addAttribute("logout", "You have successfully logged out.");
        }
        return "login";
    }
}
