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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public String   index(Map<String, Object> model) {
        Iterable<Book> books = bookRepository.findAll();
        model.put("books", books);
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
                            Map<String, Object> model) {
        bookRepository.save(new Book(title, author, publisher,
                                    LocalDate.parse(publishingDate,
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)),
                                    ISBN, quantity));
        Iterable<Book> books = bookRepository.findAll();
        model.put("books", books);

        return "catalogue";
    }

    // Problems with transactions
    @PostMapping("/book/delete")
    public String   deleteBook(@AuthenticationPrincipal User user,
                            @RequestParam String ISBN,
                            Map<String, Object> model) {
        Optional<Book> bookToDelete = bookRepository.findBookByISBN(ISBN);
        if (bookToDelete.isPresent()) {
            loanRepository.deleteLoansByBook_Id(bookToDelete.get().getId()); // every loan with book to delete will be deleted
            bookRepository.deleteBookByISBN(ISBN); // return value should be 1
        }
        Iterable<Book> books = bookRepository.findAll();
        model.put("books", books);

        return "catalogue";
    }

    @PostMapping("/book/update")
    public String   updateBook(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) String publisher,
                            @RequestParam(required = false) String publishingDate,
                            @RequestParam(required = true) String ISBN,
                            @RequestParam(required = false) Integer quantity,
                            Map<String, Object> model) {
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
        model.put("books", books);

        return "catalogue";
    }

    @PostMapping("search/{searchParameter}")
    public String   searchByTitle(@PathVariable String searchParameter,
                                  @RequestParam String parameter,
                                  Map<String, Object> model) {
        Iterable<Book> books;
        if (parameter != null && !parameter.isEmpty()) {
            if (searchParameter.equals("byTitle")) {
                books = bookRepository.findByTitle(parameter);
            } else if (searchParameter.equals("byAuthor")) {
                books = bookRepository.findByAuthor(parameter);
            } else {
                books = bookRepository.findAll();
            }
        } else {
            books = bookRepository.findAll();
        }
        model.put("books", books);

        return "catalogue";
    }

    @PostMapping("sort")
    public String   sortByProperty(@RequestParam(value = "sortProperty", required = false) String sortPropertyValue,
                                   Map<String, Object> model) {
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
        model.put("books", books);
        return "catalogue";
    }

    @GetMapping("/book/{id}")
    public String   getBook(@PathVariable String id,
                            Map<String, Object> model) {
        Optional<Book> foundBook = bookRepository.findById(Integer.valueOf(id));

        foundBook.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Book book = foundBook.get();
        model.put("id", book.getId());
        model.put("title", book.getTitle());
        model.put("author", book.getAuthor());
        model.put("publisher", book.getPublisher());
        model.put("publishingDate", book.getPublishingDate().toString());
        model.put("ISBN", book.getISBN());
        model.put("quantity", book.getQuantity());

        return "book";
    }

    @GetMapping("/order/{id}")
    public String   orderBook(@PathVariable String id,
                              @AuthenticationPrincipal User user,
                              Map<String, Object> model) {
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
                subscription = new Subscription(user);
                subscriptionRepository.save(subscription);
            }
            user.setSubscription(subscription);
            userRepository.save(user);
            Loan loan = new Loan(book, LocalDate.now(), LocalDate.now().plusDays(21), 0.0, subscription);
            loanRepository.save(loan);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("No free books of '%s' left",
                                                                                    foundBook.get().getTitle()));
        }
        model.put("books", bookRepository.findAll());
        return "catalogue";
    }
}