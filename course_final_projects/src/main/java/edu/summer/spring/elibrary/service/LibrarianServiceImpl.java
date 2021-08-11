package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.mapper.LibrarianMapper;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Librarian;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.LibrarianRepository;
import edu.summer.spring.elibrary.repository.LoanRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibrarianServiceImpl implements LibrarianService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LibrarianDto findByUsername(String username) throws FoundNoInstanceException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new FoundNoInstanceException("No user with specified username was found."));
        return LibrarianDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public LibrarianDto updatePresence(LibrarianDto librarianDto, Boolean present) {
        Librarian librarian = librarianRepository.findByUser(
                                userRepository.findByUsername(
                                        librarianDto.getUsername()
                                    ).get()
                                ).get();
        librarian.setPresent(present);
        librarianRepository.save(librarian);
        return LibrarianMapper.toLibrarianDto(librarian);
    }

    @Override
    public List<Loan> getLoansOfLibrarian(LibrarianDto librarianDto) {
        return loanRepository.findAllByLibrarian(
                    librarianRepository.findByUser(
                            userRepository.findByUsername(
                                    librarianDto.getUsername()
                            ).get()
                    ).get()
        );
    }
}
