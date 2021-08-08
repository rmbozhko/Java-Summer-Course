package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.dto.mapper.UserMapper;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Role;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.service.LibrarianService;
import edu.summer.spring.elibrary.service.LoanService;
import edu.summer.spring.elibrary.service.ReaderService;
import edu.summer.spring.elibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ReaderService readerService;

    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile/info")
    public String   getInfo(@AuthenticationPrincipal User user, Model model) {
        String info = "404";
        try {
            User userFromDb = userService.findByUsername(user.getUsername());
            model.addAttribute("user", userFromDb);
            Role role = userFromDb.getRole();
            switch (role) {
                case READER:
                    info = getReaderInfo(user, model);
                    break;
                case LIBRARIAN:
                    info = getLibrarianInfo(user, model);
                    break;
                case ADMIN:
                    info = getAdminInfo();
                    break;
                default:
                    info = "404";
            }
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return info;
    }

    private String getAdminInfo() {
        return "admin_info";
    }

    private String getLibrarianInfo(User librarian, Model model) throws FoundNoInstanceException {
        LibrarianDto librarianDto = librarianService.findByUsername(librarian.getUsername());
        model.addAttribute("loans", librarianService.getLoansOfLibrarian(librarianDto));
        model.addAttribute("readers", readerService.getAllReadersData());
        return "librarian_info";
    }

    private String getReaderInfo(User user, Model model) {
        try {
            List<Loan> loans = loanService.findAllByUser(UserMapper.toUserDto(user));
            loanService.updatePenalty(loans);
            model.addAttribute("loans", loans);
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "reader_info";
    }
}
