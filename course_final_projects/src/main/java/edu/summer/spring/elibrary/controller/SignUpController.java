package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.controller.command.UserFormCommand;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@Validated
public class SignUpController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("signup")
    public ModelAndView signUpForm() {
        return new ModelAndView("signup");
    }

    @PostMapping(path = "/signup", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ModelAndView signUpUser(@Valid UserFormCommand user) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            registerUser(user);
            modelAndView.setViewName("redirect:/login");
        } catch (NotUniqueDataException e) {
            modelAndView.addObject("message", e.getMessage() + ": " + e.getData());
            modelAndView.setViewName("signup");
        }

        return modelAndView;
    }

    private ReaderDto registerUser(UserFormCommand user) throws NotUniqueDataException {
        ReaderDto readerDto = ReaderDto.builder()
                                        .username(user.getUsername())
                                        .password(user.getPassword())
                                        .firstName(user.getFirstName())
                                        .lastName(user.getLastName())
                                        .email(user.getEmail())
                                        .build();
        return readerService.addReader(readerDto);
    }

}
