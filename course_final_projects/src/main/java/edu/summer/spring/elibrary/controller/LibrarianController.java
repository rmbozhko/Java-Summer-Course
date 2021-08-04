package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.Librarian;
import edu.summer.spring.elibrary.entity.Loan;
import edu.summer.spring.elibrary.entity.Reader;
import edu.summer.spring.elibrary.entity.User;
import edu.summer.spring.elibrary.repos.LibrarianRepository;
import edu.summer.spring.elibrary.repos.LoanRepository;
import edu.summer.spring.elibrary.repos.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/update/presence")
    public String       updatePresence(@AuthenticationPrincipal User user,
                                       @RequestParam String present,
                                       Model model) {
        model.addAttribute("user", user);
        Librarian librarian = librarianRepository.findByUser(user).get();
        if (present.equals("True")) {
            librarian.setPresent(true);
        } else if (present.equals("False")) {
            librarian.setPresent(false);
        } else {
            model.addAttribute("message", "Passed incorrect presence status");
        }
        librarianRepository.save(librarian);

        for (Loan loan : loanRepository.findAllByLibrarian(librarian)) {
            long daysBetween = DAYS.between(LocalDate.now(), loan.getEndDate());
            if (daysBetween < 0) {
                loan.setPenalty(Loan.DAILY_PENALTY_HRV * Math.abs(daysBetween));
            }
            loanRepository.save(loan);
        }
        model.addAttribute("loans", loanRepository.findAllByLibrarian(librarian));
        Map<String, User> readersData = new HashMap<>();
        for (Reader reader : readerRepository.findAll()) {
            readersData.put(reader.getSubscription().getToken(), reader.getUser());
        }
        model.addAttribute("readers", readersData);

        return "librarian_info";
    }
}
