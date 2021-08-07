package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.model.*;
import edu.summer.spring.elibrary.repository.LibrarianRepository;
import edu.summer.spring.elibrary.repository.LoanRepository;
import edu.summer.spring.elibrary.repository.ReaderRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @GetMapping("/profile/info")
    public String   getInfo(@AuthenticationPrincipal User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername()).get();
        model.addAttribute("user", userFromDb);
        Role role = userFromDb.getRole();
        String info;
        switch (role) {
            case READER:
                info = getReaderInfo(user, model);
                break;
            case LIBRARIAN:
                info = getLibrarianInfo(librarianRepository.findByUser(user).get(), model);
                break;
            case ADMIN:
                info = getAdminInfo(model);
                break;
            default:
                info = getErrorInfo(model);
        }
        return info;
    }

    private String getErrorInfo(Model model) {
        return "";
    }

    private String getAdminInfo(Model model) {
        return "admin_info";
    }

    private String getLibrarianInfo(Librarian librarian, Model model) {
        model.addAttribute("loans", loanRepository.findAllByLibrarian(librarian));
        Map<String, User> readersData = new HashMap<>();
        for (Reader reader : readerRepository.findAll()) {
            readersData.put(reader.getSubscription().getToken(), reader.getUser());
        }
        model.addAttribute("readers", readersData);

        return "librarian_info";
    }

    private String getReaderInfo(User user, Model model) {
        Optional<Reader> reader = readerRepository.findByUser(user);
        List<Loan> loans = loanRepository.findAllBySubscription(reader.get().getSubscription());
        for (Loan loan : loans) {
            long daysBetween = DAYS.between(LocalDate.now(), loan.getEndDate());
            if (daysBetween < 0) {
                loan.setPenalty(Loan.DAILY_PENALTY_HRV * Math.abs(daysBetween));
            }
            loanRepository.save(loan);
        }
        model.addAttribute("loans", loans);

        return "reader_info";
    }
}
