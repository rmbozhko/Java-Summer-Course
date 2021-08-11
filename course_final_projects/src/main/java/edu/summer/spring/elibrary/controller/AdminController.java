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
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("admin")
@Validated
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
    public ModelAndView addLibrarian(@AuthenticationPrincipal User user,
                                     @Valid UserFormCommand librarian) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile/info");
        modelAndView.addObject("user", user);
        try {
            registerLibrarian(librarian);
            modelAndView.addObject("message", "Librarian was added.");
        } catch (NotUniqueDataException e) {
            modelAndView.addObject("message", e.getMessage() + " " + e.getData());
        }
        return modelAndView;
    }

    private LibrarianDto    registerLibrarian(UserFormCommand librarian) throws NotUniqueDataException {
        LibrarianDto librarianDto = LibrarianDto.builder()
                                                .username(librarian.getUsername())
                                                .password(librarian.getPassword())
                                                .firstName(librarian.getFirstName())
                                                .lastName(librarian.getLastName())
                                                .email(librarian.getEmail())
                                                .build();
        return adminService.addLibrarian(librarianDto);
    }

    @PostMapping("delete/librarian")
    public ModelAndView deleteLibrarian(@AuthenticationPrincipal User user,
                                        @RequestParam @Pattern(regexp = "[a-zA-Z]{1,20}", message = "Not valid username") String username) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile/info");

        modelAndView.addObject("user", user);
        try {
            LibrarianDto librarianDto = librarianService.findByUsername(username);
            adminService.deleteLibrarian(librarianDto);
            modelAndView.addObject("message", "Librarian was deleted.");
        } catch (FoundNoInstanceException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("de_activate/user")
    public ModelAndView   deActivateUser(@AuthenticationPrincipal User user,
                                         @RequestParam @Pattern(regexp = "[a-zA-Z]{1,20}", message = "Not valid username") String username,
                                         @RequestParam @Pattern(regexp = "(True|False)") String active) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile/info");

        modelAndView.addObject("user", user);
        try {
            ReaderDto readerDto = readerService.findByUsername(username);
            adminService.deActiveReader(readerDto, Boolean.parseBoolean(active));
            modelAndView.addObject("message", "User's status was changed");
        } catch (FoundNoInstanceException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(path = "add/book", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ModelAndView addBook(@AuthenticationPrincipal User user,
                                @Valid BookFormCommand book) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile/info");

        modelAndView.addObject("user", user);
        // TODO solve problem with simultaneous pagination and sorting
        // TODO make page for validation errors
        try {
            adminService.addBook(fromBookFormToDto(book));
            modelAndView.addObject("message", "Book was added");
        } catch (NotUniqueDataException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }

    private BookDto fromBookFormToDto(BookFormCommand book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPublisher(book.getPublisher());
        if (book.getPublishingDate() != null) bookDto.setPublishingDate(book.getPublishingDate().toString());
        bookDto.setISBN(book.getISBN());
        if (book.getQuantity() != null) bookDto.setQuantity(book.getQuantity());

        return bookDto;
    }

    @PostMapping("delete/book")
    public ModelAndView   deleteBook(@AuthenticationPrincipal User user,
                                     @RequestParam @ISBN String ISBN) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile/info");
        modelAndView.addObject("user", user);
        try {
            BookDto bookDto = bookService.findByISBN(ISBN);
            adminService.deleteBook(bookDto);
            modelAndView.addObject("message", "Book was successfully deleted.");
        } catch (FoundNoInstanceException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("update/book")
    public ModelAndView   updateBook(@AuthenticationPrincipal User user,
                                     @Valid BookFormCommand book) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile/info");

        modelAndView.addObject("user", user);
        try {
            BookDto bookDto = fromBookFormToDto(book);
            adminService.updateBook(bookDto);
            modelAndView.addObject("message", "Book was successfully updated.");
        } catch (FoundNoInstanceException e) {
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

}
