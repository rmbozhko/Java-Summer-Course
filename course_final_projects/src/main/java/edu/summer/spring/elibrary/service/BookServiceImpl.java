package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.mapper.BookMapper;
import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Override
    public BookDto findByISBN(String isbn) throws FoundNoInstanceException {
        Book book = bookRepository.findBookByISBN(isbn)
                                    .orElseThrow(
                                            () -> new FoundNoInstanceException("No book with specified ISBN was found."));
        return new BookDto().setTitle(book.getTitle())
                            .setAuthor(book.getAuthor())
                            .setPublisher(book.getPublisher())
                            .setPublishingDate(book.getPublishingDate().toString())
                            .setISBN(isbn)
                            .setQuantity(book.getQuantity());
    }

    @Override
    public Iterable<Book> findAllByTitleOrAuthorOrISBN(String title, String author, String isbn) {
        return bookRepository.findAllByTitleOrAuthorOrISBN(title, author, isbn);
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Iterable<Book> sortByProperty(String property) {
        Iterable<Book> books;
        switch (property) {
            case "title":
                books = bookRepository.findAllByOrderByTitleAsc();
                break;
            case "author":
                books = bookRepository.findAllByOrderByAuthorAsc();
                break;
            case "publisher":
                books = bookRepository.findAllByOrderByPublisherAsc();
                break;
            case "publishingDate":
                books = bookRepository.findAllByOrderByPublishingDateDesc();
                break;
            default:
                books = bookRepository.findAll();
                break;
        }
        return books;
    }

    @Override
    public BookDto findById(Integer id) throws FoundNoInstanceException {
        Book book = bookRepository.findById(id).orElseThrow(
                                    () -> new FoundNoInstanceException("No book with specified id was found."));
        return BookMapper.toBookDto(book);
    }

    @Override
    public BookDto orderBookById(Integer id, Integer loanPeriod, User user) throws FoundNoInstanceException {
        BookDto bookDto = findById(id);
        orderBook(bookDto, user, loanPeriod);
        return findById(id);
    }

    // move ordering to services
    private void orderBook(BookDto book, User user, Integer loanPeriod) {
        if (book.getQuantity() > 0) {
            Optional<Subscription> optionalSubscription = subscriptionRepository.findOneByUserId(user.getId());
            Subscription subscription = optionalSubscription.get();
            if (!loanRepository.existsLoanByBookAndSubscription(book, subscription)) {
                book.setQuantity(book.getQuantity() - 1);
                bookRepository.save(book);
                Loan loan = new Loan(book, LocalDate.now(), LocalDate.now().plusDays(loanPeriod),
                        0.0, subscription, librarianRepository.findByPresentIsTrue().get());
                loanRepository.save(loan);
            } else {
                model.addAttribute("message", "You have already lent this book");
            }
        } else {
            model.addAttribute("message", String.format("No free books of '%s' left", book.getTitle()));
        }
    }
}
