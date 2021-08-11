package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.service.LibrarianService;
import edu.summer.spring.elibrary.service.LoanService;
import edu.summer.spring.elibrary.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/librarian")
@Validated
public class LibrarianController {
    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ReaderService readerService;

    @PostMapping("/update/presence")
    public ModelAndView updatePresence(@AuthenticationPrincipal User user,
                                       @Pattern(regexp = "(True|False)") @RequestParam String present) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile/info");
        modelAndView.addObject("user", user);
        try {
            LibrarianDto librarianDto = librarianService.findByUsername(user.getUsername());
            librarianService.updatePresence(librarianDto, Boolean.parseBoolean(present));
            modelAndView.addObject("message", "Librarian presence status was updated");
            loanService.updatePenalty(librarianService.getLoansOfLibrarian(librarianDto));
            modelAndView.addObject("loans", librarianService.getLoansOfLibrarian(librarianDto));
            modelAndView.addObject("readers", readerService.getAllReadersData());
        } catch (FoundNoInstanceException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }
}
