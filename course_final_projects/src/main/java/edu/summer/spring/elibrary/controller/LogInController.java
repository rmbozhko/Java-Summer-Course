package edu.summer.spring.elibrary.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("login")
public class LogInController {

    @GetMapping
    public ModelAndView logIn(@RequestParam(name = "error", required = false) String error,
                              @RequestParam(name = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (error != null) {
            modelAndView.addObject("error", "Bad credentials.");
        } else if (logout != null) {
            modelAndView.addObject("logout", "You have successfully logged out.");
        }
        return modelAndView;
    }
}
