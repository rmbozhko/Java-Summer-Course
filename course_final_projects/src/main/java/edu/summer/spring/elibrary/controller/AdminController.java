package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.controller.command.BookFormCommand;
import edu.summer.spring.elibrary.controller.command.UserFormCommand;
import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.service.AdminServiceImpl;
import edu.summer.spring.elibrary.service.BookService;
import edu.summer.spring.elibrary.service.LibrarianService;
import edu.summer.spring.elibrary.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BookService bookService;

    @PostMapping(path = "/add/librarian", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String   addLibrarian(@AuthenticationPrincipal User user,
                                 UserFormCommand librarian,
                                 Model model) {
        model.addAttribute("user", user);
        try {
            registerLibrarian(librarian);
            model.addAttribute("message", "Librarian was added.");
        } catch (NotUniqueDataException e) {
            model.addAttribute("message", e.getMessage() + " " + e.getData());
        }
        return "redirect:/user/profile/info";
    }

    private LibrarianDto    registerLibrarian(UserFormCommand librarian) throws NotUniqueDataException {
        LibrarianDto librarianDto = new LibrarianDto().setUsername(librarian.getUsername())
                                .setPassword(librarian.getPassword())
                                .setFirstName(librarian.getFirstName())
                                .setLastName(librarian.getLastName())
                                .setEmail(librarian.getEmail());
        return adminService.addLibrarian(librarianDto);
    }

    @PostMapping("delete/librarian")
    public String   deleteLibrarian(@AuthenticationPrincipal User user,
                                    @RequestParam String username,
                                    Model model) {
        model.addAttribute("user", user);
        try {
            LibrarianDto librarianDto = librarianService.findByUsername(username);
            adminService.deleteLibrarian(librarianDto);
            model.addAttribute("message", "Librarian was deleted.");
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "redirect:/user/profile/info";
    }

    @PostMapping("de_activate/user")
    public String   deActivateUser(@AuthenticationPrincipal User user,
                                   @RequestParam String username,
                                   @Pattern(regexp = "[(True|False)]") @RequestParam String active,
                                   Model model) {
        // active: True | False - Hibernate Validator
        model.addAttribute("user", user);
        try {
            ReaderDto readerDto = readerService.findByUsername(username);
            adminService.deActiveReader(readerDto, Boolean.parseBoolean(active));
            model.addAttribute("message", "User's status was changed");
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "redirect:/user/profile/info";
    }

    @PostMapping(path = "add/book", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String   addBook(@AuthenticationPrincipal User user,
                            @Valid BookFormCommand book,
                            Model model,
                            BindingResult bindingResult) {
        model.addAttribute("user", user);
        // pagination
        // PRG
        // Validation
        if (bindingResult.hasErrors()) {
            // TODO Validate form parameters
            // handle if there are errors: use Bootstrap :valid, :invalid for input which contains errors
        } else {
            try {
                adminService.addBook(registerBook(book));
                model.addAttribute("message", "Book was added");
            } catch (NotUniqueDataException e) {
                model.addAttribute("message", e.getMessage());
            }
        }
        return "redirect:/user/profile/info";
    }

    private BookDto registerBook(BookFormCommand book) {
        return new BookDto().setTitle(book.getTitle())
                            .setAuthor(book.getAuthor())
                            .setPublisher(book.getPublisher())
                            .setPublishingDate(book.getPublishingDate())
                            .setISBN(book.getISBN())
                            .setQuantity(book.getQuantity());
    }

    @PostMapping("delete/book")
    public String   deleteBook(@AuthenticationPrincipal User user,
                               @RequestParam String ISBN,
                               Model model) {
        model.addAttribute("user", user);
//        if (bindingResult.hasErrors()) {
            // validate RequestParam
//        } else {
            try {
                BookDto bookDto = bookService.findByISBN(ISBN);
                adminService.deleteBook(bookDto);
                model.addAttribute("message", "Book was successfully deleted.");
            } catch (FoundNoInstanceException e) {
                model.addAttribute("message", e.getMessage());
            }
//        }
        return "redirect:/user/profile/info";
    }

    @PostMapping("update/book")
    public String   updateBook(@AuthenticationPrincipal User user,
                               BookFormCommand book,
                               Model model) {
        model.addAttribute("user", user);
        try {
            BookDto bookDto = getBookFromUpdateForm(book);
            adminService.updateBook(bookDto);
            model.addAttribute("message", "Book was successfully updated.");
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "redirect:/user/profile/info";
    }

    private BookDto getBookFromUpdateForm(BookFormCommand bookFormCommand) {
        return new BookDto().setTitle(bookFormCommand.getTitle())
                            .setAuthor(bookFormCommand.getAuthor())
                            .setPublisher(bookFormCommand.getPublisher())
                            .setPublishingDate(bookFormCommand.getPublishingDate())
                            .setISBN(bookFormCommand.getISBN())
                            .setQuantity(bookFormCommand.getQuantity());
    }

}
