package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.User;

public interface UserService {
    User findByUsername(String username) throws FoundNoInstanceException;
}
