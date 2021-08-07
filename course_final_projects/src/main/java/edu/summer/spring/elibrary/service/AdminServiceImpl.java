package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.controller.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.dto.mapper.LibrarianMapper;
import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.model.Librarian;
import edu.summer.spring.elibrary.model.Role;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.LibrarianRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

@Component
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("bcryptPasswordEncoder")
    private PasswordEncoder encoder;

    @Autowired
    private LibrarianRepository librarianRepository;

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
    public String deleteLibrarian(LibrarianDto librarianDto) {
        return null;
    }

    @Override
    public String deActiveReader(ReaderDto readerDto) {
        return null;
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
