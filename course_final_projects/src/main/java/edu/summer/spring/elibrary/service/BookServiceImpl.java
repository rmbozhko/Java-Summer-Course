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
import edu.summer.spring.elibrary.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private TransactionalEntityManager entityManager;

    @Override
    public BookDto findByISBN(String isbn) throws FoundNoInstanceException {
        Book book = bookRepository.findBookByISBN(isbn)
                                    .orElseThrow(
                                            () -> new FoundNoInstanceException("No book with specified ISBN was found."));
        return BookMapper.toBookDto(book);
    }

    @Override
    public List<Book> findAllByTitleOrAuthorOrISBN(String title, String author, String isbn) {
        return bookRepository.findAllByTitleOrAuthorOrISBN(title, author, isbn);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> sortByProperty(String property) {
        Pageable pageWithSortedContent;
        switch (property) {
            case "title":
                pageWithSortedContent = PageRequest.of(0, 20, Sort.by("title").ascending());
                break;
            case "author":
                pageWithSortedContent = PageRequest.of(0, 20, Sort.by("author").ascending());
                break;
            case "publisher":
                pageWithSortedContent = PageRequest.of(0, 20, Sort.by("publisher").ascending());
                break;
            case "publishingDate":
                pageWithSortedContent = PageRequest.of(0, 20, Sort.by("publishingDate").descending());
                break;
            default:
                pageWithSortedContent = PageRequest.of(0, 20);
                break;
        }
        return bookRepository.findAll(pageWithSortedContent);
    }

    @Override
    public BookDto findById(Integer id) throws FoundNoInstanceException {
        Book book = bookRepository.findById(id).orElseThrow(
                                    () -> new FoundNoInstanceException("No book with specified id was found."));
        return BookMapper.toBookDto(book);
    }

    @Override
    public Page<Book> getBooksFromCataloguePage(Integer page) {
        int pageNumber = page - 1;
        Pageable cataloguePage = PageRequest.of(pageNumber, 5);
        return bookRepository.findAll(cataloguePage);
    }

    @Override
    public BookDto orderBookById(Integer id, Integer loanPeriod, User user) throws FoundNoInstanceException, LoanDuplicateException, NoFreeBookException {
        BookDto bookDto = findById(id);
        if (bookDto.getQuantity() > 0) {
            Subscription subscription = subscriptionRepository.findByUser(user).orElseThrow(
                    () -> new FoundNoInstanceException("Found no subscription of " + user.getUsername())
            );
            Book book = bookRepository.findBookByISBN(bookDto.getISBN()).orElseThrow(
                    () -> new FoundNoInstanceException("Found no book with ISBN " + bookDto.getISBN())
            );
            return BookMapper.toBookDto(orderBook(subscription, book, loanPeriod));
        } else {
            throw new NoFreeBookException(String.format("There is no free books of '%s' left", bookDto.getTitle()));
        }
    }

    public Book orderBook(Subscription subscription, Book book, Integer loanPeriod) throws LoanDuplicateException {
        book.setQuantity(book.getQuantity() - 1);
        Loan loan = new Loan(book, LocalDate.now(), LocalDate.now().plusDays(loanPeriod),
                0.0, subscription, librarianRepository.findByPresentIsTrue().get()); // assume there is always one librarian on duty
        try {
            entityManager.saveEntity(bookRepository, book);
            entityManager.saveEntity(loanRepository, loan);
        } catch (DataIntegrityViolationException e) {
            throw new LoanDuplicateException("You have already lent this book");
        }
        return book;
    }
}
