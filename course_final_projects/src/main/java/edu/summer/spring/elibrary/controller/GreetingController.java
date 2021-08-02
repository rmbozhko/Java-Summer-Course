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
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
public class GreetingController {

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

    @PostMapping("/book/add")
    public String   addBook(@AuthenticationPrincipal User user,
                            @RequestParam String title,
                            @RequestParam String author,
                            @RequestParam String publisher,
                            @RequestParam String publishingDate,
                            @RequestParam String ISBN,
                            @RequestParam Integer quantity,
                            Model model) {
        bookRepository.save(new Book(title, author, publisher,
                                    LocalDate.parse(publishingDate,
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)),
                                    ISBN, quantity));
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);

        return "catalogue";
    }

    // Problems with transactions
    @PostMapping("/book/delete")
    public String   deleteBook(@AuthenticationPrincipal User user,
                            @RequestParam String ISBN,
                            Model model) {
        Optional<Book> bookToDelete = bookRepository.findBookByISBN(ISBN);
        if (bookToDelete.isPresent()) {
            loanRepository.deleteLoansByBook(bookToDelete.get()); // every loan with book to delete will be deleted
            bookRepository.deleteBookByISBN(ISBN); // return value should be 1
        }
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);

        return "catalogue";
    }

    @PostMapping("/book/update")
    public String   updateBook(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) String publisher,
                            @RequestParam(required = false) String publishingDate,
                            @RequestParam(required = true) String ISBN,
                            @RequestParam(required = false) Integer quantity,
                            Model model) {
        Optional<Book> bookToUpdate = bookRepository.findBookByISBN(ISBN);
        if (bookToUpdate.isPresent()) {
            Book book = bookToUpdate.get();
            if (!title.isEmpty()) book.setTitle(title);
            if (!author.isEmpty()) book.setAuthor(author);
            if (!publisher.isEmpty()) book.setPublisher(publisher);
            if (!publishingDate.isEmpty()) book.setPublishingDate(LocalDate.parse(publishingDate,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)));
            if (!ISBN.isEmpty()) book.setISBN(ISBN);
            if (quantity != null && quantity > 0) book.setQuantity(quantity);
            bookRepository.save(book);
        }
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

        foundBook.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Book book = foundBook.get();
        model.addAttribute("id", book.getId());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("publisher", book.getPublisher());
        model.addAttribute("publishingDate", book.getPublishingDate().toString());
        model.addAttribute("ISBN", book.getISBN());
        model.addAttribute("quantity", book.getQuantity());

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