package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.mapper.BookMapper;
import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.LoanDuplicateException;
import edu.summer.spring.elibrary.exception.NoFreeBookException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.BookRepository;
import edu.summer.spring.elibrary.repository.LibrarianRepository;
import edu.summer.spring.elibrary.repository.LoanRepository;
import edu.summer.spring.elibrary.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

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
    public BookDto orderBookById(Integer id, Integer loanPeriod, User user) throws FoundNoInstanceException, LoanDuplicateException, NoFreeBookException {
        BookDto bookDto = findById(id);
        orderBook(bookDto, user, loanPeriod);
        return findById(id);
    }

    private void orderBook(BookDto bookDto, User user, Integer loanPeriod) throws FoundNoInstanceException, LoanDuplicateException, NoFreeBookException {
        if (bookDto.getQuantity() > 0) {
            Subscription subscription = subscriptionRepository.findByUser(user).orElseThrow(
                    () -> new FoundNoInstanceException("Found no subscription of " + user.getUsername())
            );
            Book book = bookRepository.findBookByISBN(bookDto.getISBN()).orElseThrow(
                    () -> new FoundNoInstanceException("Found no book with ISBN " + bookDto.getISBN())
            );
            if (!loanRepository.existsLoanByBookAndSubscription(book, subscription)) {
                book.setQuantity(book.getQuantity() - 1);
                bookRepository.save(book);
                Loan loan = new Loan(book, LocalDate.now(), LocalDate.now().plusDays(loanPeriod),
                        0.0, subscription, librarianRepository.findByPresentIsTrue().get()); // assume there is always one librarian on duty
                loanRepository.save(loan);
            } else {
                throw new LoanDuplicateException("You have already lent this book");
            }
        } else {
             throw new NoFreeBookException(String.format("There is no free books of '%s' left", bookDto.getTitle()));
        }
    }
}
