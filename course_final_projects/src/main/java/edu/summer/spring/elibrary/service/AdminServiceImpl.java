package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.mapper.BookMapper;
import edu.summer.spring.elibrary.dto.mapper.LibrarianMapper;
import edu.summer.spring.elibrary.dto.mapper.ReaderMapper;
import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.model.*;
import edu.summer.spring.elibrary.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component // TODO replace with @Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("bcryptPasswordEncoder")
    private PasswordEncoder encoder;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public LibrarianDto addLibrarian(LibrarianDto librarianDto) throws NotUniqueDataException {
        Optional<User> userFromDb = userRepository.findByUsername(librarianDto.getUsername());
        if (userFromDb.isPresent()) {
            throw new NotUniqueDataException("Not unique username", userFromDb.get().getUsername());
        } else {
            // TODO Replace with Builder
            User librarianUserEntity = new User(librarianDto.getUsername(),
                                                encoder.encode(librarianDto.getPassword()),
                                                librarianDto.getFirstName(),
                                                librarianDto.getLastName(),
                                                librarianDto.getEmail());
            librarianUserEntity.setActive(true);
            librarianUserEntity.setRole(Role.LIBRARIAN);
            userRepository.save(librarianUserEntity);
            Librarian librarian = new Librarian(false, librarianUserEntity);
            return LibrarianMapper.toLibrarianDto(librarianRepository.save(librarian));
        }
    }

    @Override
    public LibrarianDto deleteLibrarian(LibrarianDto librarianDto) throws FoundNoInstanceException {
        User userToDelete = userRepository.findByUsername(librarianDto.getUsername())
                                            .orElseThrow(
                                                    () -> new FoundNoInstanceException("No user with specified username was found."));
        Optional<Librarian> librarian = librarianRepository.findByUser(userToDelete);
        Librarian librarianToDelete = librarian.orElseThrow(() -> new FoundNoInstanceException("User with specified username isn't librarian"));
        List<Loan> loansOfLibrarian = loanRepository.findAllByLibrarian(librarianToDelete);
        Librarian librarianPresent = librarianRepository.findByPresentIsTrue().get(); // suppose, there should always be librarian on duty
        for (Loan loan : loansOfLibrarian) {
            loan.setLibrarian(librarianPresent);
            loanRepository.save(loan);
        }
        librarianRepository.delete(librarianToDelete);
        userRepository.delete(userToDelete);
        return LibrarianMapper.toLibrarianDto(librarianToDelete);
    }

    @Override
    public ReaderDto deActiveReader(ReaderDto readerDto, Boolean active) throws FoundNoInstanceException {
        User userToChangeStatus = userRepository.findByUsername(readerDto.getUsername())
                                                            .orElseThrow(
                                                                    () -> new FoundNoInstanceException("No user with specified username was found."));
        userToChangeStatus.setActive(active);
        userRepository.save(userToChangeStatus);
        return ReaderMapper.toReaderDto(readerRepository.findByUser(userToChangeStatus).get());
    }

    @Override
    public BookDto addBook(BookDto bookDto) throws NotUniqueDataException {
        Optional<Book> bookFromDb = bookRepository.findBookByISBN(bookDto.getISBN());
        if (bookFromDb.isPresent()) {
            throw new NotUniqueDataException("Not unique ISBN", bookFromDb.get().getISBN());
        } else {
            Book book = new Book(bookDto.getTitle(),
                    bookDto.getAuthor(),
                    bookDto.getPublisher(),
                    LocalDate.parse(bookDto.getPublishingDate(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)),
                    bookDto.getISBN(),
                    bookDto.getQuantity());
            return BookMapper.toBookDto(bookRepository.save(book));
        }
    }

    @Override
    public BookDto deleteBook(BookDto bookDto) throws FoundNoInstanceException {
        Book book = bookRepository.findBookByISBN(bookDto.getISBN())
                                .orElseThrow(() -> new FoundNoInstanceException("No book found with specified ISBN."));
        loanRepository.deleteLoansByBook(book); // every loan with book to delete will be deleted
        bookRepository.delete(book); // return value should be 1
        return BookMapper.toBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) throws FoundNoInstanceException {
        Book book = bookRepository.findBookByISBN(bookDto.getISBN())
                                .orElseThrow(() -> new FoundNoInstanceException("No book with specified ISBN was found."));

        if (bookDto.getTitle() != null && !bookDto.getTitle().isEmpty()) book.setTitle(bookDto.getTitle());
        if (bookDto.getAuthor() != null && !bookDto.getAuthor().isEmpty()) book.setAuthor(bookDto.getAuthor());
        if (bookDto.getPublisher() != null && !bookDto.getPublisher().isEmpty()) book.setPublisher(bookDto.getPublisher());
        if (bookDto.getPublishingDate() != null && !bookDto.getPublishingDate().isEmpty()) {
            book.setPublishingDate(LocalDate.parse(bookDto.getPublishingDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)));
        }
        if (bookDto.getQuantity() != null && bookDto.getQuantity() > 0) book.setQuantity(bookDto.getQuantity());
        bookRepository.save(book);
        return BookMapper.toBookDto(book);
    }
}
