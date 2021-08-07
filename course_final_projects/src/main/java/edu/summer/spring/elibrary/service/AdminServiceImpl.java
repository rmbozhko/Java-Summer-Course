package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.mapper.LibrarianMapper;
import edu.summer.spring.elibrary.dto.mapper.ReaderMapper;
import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.model.Librarian;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Role;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.LibrarianRepository;
import edu.summer.spring.elibrary.repository.LoanRepository;
import edu.summer.spring.elibrary.repository.ReaderRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
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

    @Override
    public LibrarianDto addLibrarian(LibrarianDto librarianDto) throws NotUniqueDataException {
        Optional<User> userFromDb = userRepository.findByUsername(librarianDto.getUsername());
        if (userFromDb.isPresent()) {
            throw new NotUniqueDataException("Not unique username", userFromDb.get().getUsername());
        } else {
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
    public String addBook(BookDto bookDto) {
        return null;
    }

    @Override
    public String deleteBook(BookDto bookDto) {
        return null;
    }

    @Override
    public String updateBook(BookDto bookDto) {
        return null;
    }
}
