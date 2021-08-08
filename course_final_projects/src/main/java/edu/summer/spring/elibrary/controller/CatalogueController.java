package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.*;
import edu.summer.spring.elibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class CatalogueController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private BookService bookService;

    @GetMapping
    public String   index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "catalogue";
    }

    @PostMapping("search")
    public String   searchByTitle(@RequestParam(defaultValue = "") String parameter,
                                  Model model) {
        Iterable<Book> books = (!parameter.isEmpty()) ? bookService.findAllByTitleOrAuthorOrISBN(parameter, parameter, parameter)
                                        : bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("parameter", parameter);
        return "catalogue";
    }

    @PostMapping("sort")
    public String   sortByProperty(@RequestParam(value = "sortProperty", required = false, defaultValue = "") String sortPropertyValue,
                                   Model model) {
        model.addAttribute("books", bookService.sortByProperty(sortPropertyValue));
        return "catalogue";
    }

    @GetMapping("/book/{id}")
    public String   getBook(@PathVariable String id,
                            Model model) {
        try {
            BookDto bookDto = bookService.findById(Integer.valueOf(id));
            model.addAttribute("book", bookDto);
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "book";
    }

    // only for user-readers
    @PostMapping("/order/{id}")
    public String   orderBookById(@AuthenticationPrincipal User user,
                                  @PathVariable String id,
                                  @RequestParam(name = "loan_period") String loanPeriod,
                                  Model model) {
        try {
            BookDto bookDto = bookService.orderBookById(Integer.valueOf(id), Integer.parseInt(loanPeriod), user);
            model.addAttribute("message", "You have successfully lent the book");
            model.addAttribute("books", bookService.findAll());
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "catalogue";
    }
}