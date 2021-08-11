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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
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
    public ModelAndView getInfo(@AuthenticationPrincipal User user, Model model) {
        ModelAndView modelAndView = new ModelAndView("404");
        try {
            User userFromDb = userService.findByUsername(user.getUsername());
            model.addAttribute("user", userFromDb);
            Role role = userFromDb.getRole();
            switch (role) {
                case READER:
                    modelAndView.setViewName(getReaderInfo(user, model));
                    break;
                case LIBRARIAN:
                    modelAndView.setViewName(getLibrarianInfo(user, model));
                    break;
                case ADMIN:
                    modelAndView.setViewName(getAdminInfo());
                    break;
                default:
                    modelAndView.setViewName("404");
            }
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return modelAndView;
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
