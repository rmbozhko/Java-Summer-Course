package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Reader;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.LibrarianRepository;
import edu.summer.spring.elibrary.repository.LoanRepository;
import edu.summer.spring.elibrary.repository.ReaderRepository;
import edu.summer.spring.elibrary.service.LibrarianService;
import edu.summer.spring.elibrary.service.LoanService;
import edu.summer.spring.elibrary.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller

@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ReaderService readerService;

    @PostMapping("/update/presence")
    public String       updatePresence(@AuthenticationPrincipal User user,
                                       @Pattern(regexp = "[(True|False)]") @RequestParam String present,
                                       Model model) {
        model.addAttribute("user", user);
        try {
            LibrarianDto librarianDto = librarianService.findByUsername(user.getUsername());
            librarianService.updatePresence(librarianDto, Boolean.parseBoolean(present));
            model.addAttribute("message", "Librarian presence status was updated");
            loanService.updatePenalty(librarianService.getLoansOfLibrarian(librarianDto));
            model.addAttribute("loans", librarianService.getLoansOfLibrarian(librarianDto));
            model.addAttribute("readers", readerService.getAllReadersData());
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "librarian_info";
    }
}
