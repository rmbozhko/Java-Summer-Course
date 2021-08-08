package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.controller.command.UserFormCommand;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignUpController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("signup")
    public String   signUpForm() {
        return "signup";
    }

//    TODO: Fix bug with sign up (doesn't get called when button is pressed)
    @PostMapping(path = "signup", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String   signUpUser(UserFormCommand user, Model model) {
        try {
            registerUser(user);
            return "redirect:/";
        } catch (NotUniqueDataException e) {
            model.addAttribute("message", e.getMessage() + ": " + e.getData());
            return "signup";
        }
    }

    private ReaderDto registerUser(UserFormCommand user) throws NotUniqueDataException {
        ReaderDto readerDto = new ReaderDto().setUsername(user.getUsername())
                            .setPassword(user.getPassword())
                            .setFirstName(user.getFirstName())
                            .setLastName(user.getLastName())
                            .setEmail(user.getEmail());
        return readerService.addReader(readerDto);
    }

}
