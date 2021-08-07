package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.controller.request.UserSignupRequest;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.model.*;
import edu.summer.spring.elibrary.repository.BookRepository;
import edu.summer.spring.elibrary.repository.LibrarianRepository;
import edu.summer.spring.elibrary.repository.LoanRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import edu.summer.spring.elibrary.service.AdminServiceImpl;
import edu.summer.spring.elibrary.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private LibrarianService librarianService;

    @PostMapping(path = "/add/librarian", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String   addLibrarian(@AuthenticationPrincipal User user,
                                 UserSignupRequest librarian,
                                 Model model) {
        model.addAttribute("user", user);
        try {
            registerLibrarian(librarian);
            model.addAttribute("message", "Librarian was added.");
        } catch (NotUniqueDataException e) {
            model.addAttribute("message", e.getMessage() + " " + e.getLoginData());
        }
        return "admin_info";
    }

    private LibrarianDto    registerLibrarian(UserSignupRequest librarian) throws NotUniqueDataException {
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
        return "admin_info";
    }

    @PostMapping("de_activate/user")
    public String   deActivateUser(@AuthenticationPrincipal User user,
                                   @RequestParam String username,
                                   @RequestParam String active,
                                   Model model) {
        model.addAttribute("user", user);
        if (active.equals("True") || active.equals("False")) {
            Optional<User> userToChangeStatus = userRepository.findByUsername(username);
            userToChangeStatus.ifPresentOrElse(
                    usr -> {
                        usr.setActive(Boolean.parseBoolean(active));
                        userRepository.save(usr);
                        model.addAttribute("message", "User's status was changed");
                    },
                    () -> model.addAttribute("message", "No user with specified username was found."));
        } else {
            model.addAttribute("message", "Incorrect status");
        }
        return "admin_info";
    }

    @PostMapping("add/book")
    public String   addBook(@AuthenticationPrincipal User user,
                            @RequestParam String title,
                            @RequestParam String author,
                            @RequestParam String publisher,
                            @RequestParam String publishingDate,
                            @RequestParam String ISBN,
                            @RequestParam Integer quantity,
                            Model model) {
        model.addAttribute("user", user);
        // TODO Validate form parameters
        bookRepository.save(new Book(title, author, publisher,
                                    LocalDate.parse(publishingDate,
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)),
                                    ISBN, quantity));
        model.addAttribute("message", "Book was added");
        return "admin_info";
    }

    @PostMapping("delete/book")
    public String   deleteBook(@AuthenticationPrincipal User user,
                               @RequestParam String ISBN,
                               Model model) {
        model.addAttribute("user", user);
        if (ISBN != null && !ISBN.isEmpty()) {
            Optional<Book> bookToDelete = bookRepository.findBookByISBN(ISBN);
            bookToDelete.ifPresentOrElse(
                    book -> {
                        loanRepository.deleteLoansByBook(book); // every loan with book to delete will be deleted
                        bookRepository.delete(book); // return value should be 1
                        model.addAttribute("message", "Book was successfully deleted.");
                    },
                    () -> model.addAttribute("message", "No book with specified ISBN was found.")
            );
        } else {
            model.addAttribute("message", "Incorrect ISBN.");
        }
        return "admin_info";
    }

    @PostMapping("update/book")
    public String   updateBook(@AuthenticationPrincipal User user,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String author,
                               @RequestParam(required = false) String publisher,
                               @RequestParam(required = false) String publishingDate,
                               @RequestParam(required = true) String ISBN,
                               @RequestParam(required = false) Integer quantity,
                               Model model) {
        model.addAttribute("user", user);
        if (ISBN != null && !ISBN.isEmpty()) {
            Optional<Book> bookToUpdate = bookRepository.findBookByISBN(ISBN);
            bookToUpdate.ifPresentOrElse(
                    book -> {
                        if (title != null && !title.isEmpty()) book.setTitle(title);
                        if (author != null && !author.isEmpty()) book.setAuthor(author);
                        if (publisher != null && !publisher.isEmpty()) book.setPublisher(publisher);
                        if (publishingDate != null && !publishingDate.isEmpty()) {
                            book.setPublishingDate(LocalDate.parse(publishingDate,
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)));
                        }
                        book.setISBN(ISBN);
                        if (quantity != null && quantity > 0) book.setQuantity(quantity);
                        bookRepository.save(book);
                        model.addAttribute("message", "Book was successfully updated.");
                    },
                    () -> model.addAttribute("message", "No book with specified ISBN was found."));
        } else {
            model.addAttribute("message", "Incorrect ISBN.");
        }

        return "admin_info";
    }

}
