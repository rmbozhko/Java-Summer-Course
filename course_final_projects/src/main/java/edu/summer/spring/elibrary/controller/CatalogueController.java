package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.entity.*;
import edu.summer.spring.elibrary.repos.*;
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

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

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

    // only for user-readers
    @PostMapping("/order/{id}")
    public String   orderBookById(@AuthenticationPrincipal User user,
                                  @PathVariable String id,
                                  @RequestParam(name = "loan_period") String loanPeriod,
                                  Model model) {
        Optional<Book> foundBook = bookRepository.findById(Integer.valueOf(id));
        foundBook.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        foundBook.ifPresentOrElse(
                book -> orderBook(book, user, Integer.parseInt(loanPeriod), model),
                () -> model.addAttribute("message", "No book found with specified id")
        );
        model.addAttribute("books", bookRepository.findAll());
        return "catalogue";

    }

    private void orderBook(Book book, User user, Integer loanPeriod, Model model) {
        if (book.getQuantity() > 0) {
            Optional<Subscription> optionalSubscription = subscriptionRepository.findOneByUserId(user.getId());
            Subscription subscription = optionalSubscription.get();
            if (!loanRepository.existsLoanByBookAndSubscription(book, subscription)) {
                book.setQuantity(book.getQuantity() - 1);
                bookRepository.save(book);
                Loan loan = new Loan(book, LocalDate.now(), LocalDate.now().plusDays(loanPeriod),
                        0.0, subscription, librarianRepository.findByPresentIsTrue().get());
                loanRepository.save(loan);
                model.addAttribute("message", "You have successfully lent the book");
            } else {
                model.addAttribute("message", "You have already lent this book");
            }
        } else {
            model.addAttribute("message", String.format("No free books of '%s' left", book.getTitle()));
        }
    }
}