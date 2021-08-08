package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository  userRepository;

    @Override
    public User findByUsername(String username) throws FoundNoInstanceException {
        User user = userRepository.findByUsername(username).orElseThrow(
                        () -> new FoundNoInstanceException("No user found with specified username"));
        return user;
    }
}
