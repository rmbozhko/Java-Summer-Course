package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.Book;
import edu.summer.spring.elibrary.entity.Loan;
import edu.summer.spring.elibrary.entity.Subscription;
import edu.summer.spring.elibrary.entity.User;
import edu.summer.spring.elibrary.repos.BookRepository;
import edu.summer.spring.elibrary.repos.LoanRepository;
import edu.summer.spring.elibrary.repos.SubscriptionRepository;
import edu.summer.spring.elibrary.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping
    public String   index(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "catalogue";
    }

    @PostMapping("search")
    public String   searchByTitle(@RequestParam(defaultValue = "") String parameter,
                                  Model model) {
        Iterable<Book> books;
        if (parameter != null && !parameter.isEmpty()) {
            books = bookRepository.findAllByTitleOrAuthorOrISBN(parameter, parameter, parameter);
        } else {
            books = bookRepository.findAll();
        }
        model.addAttribute("books", books);
        model.addAttribute("parameter", parameter);
        return "catalogue";
    }

    @PostMapping("sort")
    public String   sortByProperty(@RequestParam(value = "sortProperty", required = false) String sortPropertyValue,
                                   Model model) {
        Iterable<Book> books;
        if (sortPropertyValue == null) {
            books = bookRepository.findAll();
        } else if (sortPropertyValue.equals("title")) {
            books = bookRepository.findAllByOrderByTitleAsc();
        } else if (sortPropertyValue.equals("author")) {
            books = bookRepository.findAllByOrderByAuthorAsc();
        } else if (sortPropertyValue.equals("publisher")) {
            books = bookRepository.findAllByOrderByPublisherAsc();
        } else if (sortPropertyValue.equals("publishingDate")) {
            books = bookRepository.findAllByOrderByPublishingDateDesc();
        } else {
            books = bookRepository.findAll();
        }
        model.addAttribute("books", books);
        return "catalogue";
    }

    @GetMapping("/book/{id}")
    public String   getBook(@PathVariable String id,
                            Model model) {
        Optional<Book> foundBook = bookRepository.findById(Integer.valueOf(id));

        foundBook.ifPresentOrElse(
                book -> {
                    model.addAttribute("id", book.getId());
                    model.addAttribute("title", book.getTitle());
                    model.addAttribute("author", book.getAuthor());
                    model.addAttribute("publisher", book.getPublisher());
                    model.addAttribute("publishingDate", book.getPublishingDate().toString());
                    model.addAttribute("ISBN", book.getISBN());
                    model.addAttribute("quantity", book.getQuantity());
                },
                () -> {
                    model.addAttribute("message", "No book with specified id was found.");
                });
        return "book";
    }

    @PostMapping("/order/{id}")
    public String   orderBook(@AuthenticationPrincipal User user,
                              @PathVariable String id,
                              @RequestParam(name = "loan_period") String loanPeriod,
                              Model model) {
        Optional<Book> foundBook = bookRepository.findById(Integer.valueOf(id));
        foundBook.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Book book;
        if (foundBook.get().getQuantity() > 0) {
            book = foundBook.get();
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
            Subscription subscription;
            if (subscriptionRepository.existsSubscriptionByUserId(user.getId())) {
                subscription = subscriptionRepository.findOneByUserId(user.getId());
            } else {
                subscription = new Subscription(user, UUID.randomUUID().toString());
                subscriptionRepository.save(subscription);
                user.setSubscription(subscription);
                userRepository.save(user);
            }
            if (!loanRepository.existsLoanByBookAndSubscription(book, subscription)) {
                Loan loan = new Loan(book, LocalDate.now(), LocalDate.now().plusDays(Integer.parseInt(loanPeriod)),
                                0.0, subscription);
                loanRepository.save(loan);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("No free books of '%s' left",
                                                                                    foundBook.get().getTitle()));
        }
        model.addAttribute("books", bookRepository.findAll());
        return "catalogue";
    }
}