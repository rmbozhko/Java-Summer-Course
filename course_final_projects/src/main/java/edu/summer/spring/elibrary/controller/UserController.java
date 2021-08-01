package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.Loan;
import edu.summer.spring.elibrary.entity.Role;
import edu.summer.spring.elibrary.entity.User;
import edu.summer.spring.elibrary.repos.LoanRepository;
import edu.summer.spring.elibrary.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/profile/info")
    public String   getInfo(@AuthenticationPrincipal User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        model.addAttribute("user", userFromDb);
//        Set<Role> roles = userFromDb.getRole();
//        String info;
//        if (roles.contains(Role.READER)) {
//            info = getReaderInfo();
//        } else if (roles.contains(Role.SUPERVISOR)) {
//            info = getSupervisorInfo();
//        } else if (roles.contains(Role.ADMIN)) {
//            info = getAdminInfo();
//        } else {
//            info = getErrorInfo();
//        }
        List<Loan> loans = loanRepository.findAllBySubscription(user.getSubscription());
        for (Loan loan : loans) {
            long daysBetween = DAYS.between(LocalDate.now(), loan.getEndDate());
            if (daysBetween < 0) {
                loan.setPenalty(Loan.DAILY_PENALTY_HRV * Math.abs(daysBetween));
            }
            loanRepository.save(loan);
        }
        model.addAttribute("loans", loans);
        return "profile_info";
    }
}
