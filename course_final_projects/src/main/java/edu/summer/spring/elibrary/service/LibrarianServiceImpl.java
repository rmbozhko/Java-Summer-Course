package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibrarianServiceImpl implements LibrarianService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public LibrarianDto findByUsername(String username) throws FoundNoInstanceException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new FoundNoInstanceException("No user with specified username was found."));
        return new LibrarianDto().setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail());
    }
}
