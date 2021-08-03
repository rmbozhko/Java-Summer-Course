package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.Loan;
import edu.summer.spring.elibrary.entity.Reader;
import edu.summer.spring.elibrary.entity.Role;
import edu.summer.spring.elibrary.entity.User;
import edu.summer.spring.elibrary.repos.LoanRepository;
import edu.summer.spring.elibrary.repos.ReaderRepository;
import edu.summer.spring.elibrary.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
                info = getLibrarianInfo(user, model);
                break;
            case ADMIN:
                info = getAdminInfo(user, model);
                break;
            default:
                info = getErrorInfo(user, model);
        }
        return info;
    }

    private String getErrorInfo(User user, Model model) {
        return "";
    }

    private String getAdminInfo(User user, Model model) {
        return "admin_info";
    }

    private String getLibrarianInfo(User user, Model model) {
        model.addAttribute("loans", loanRepository.findAll());
        model.addAttribute("users", userRepository.findAllByRole(Role.READER));

        return "supervisor_info";
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
