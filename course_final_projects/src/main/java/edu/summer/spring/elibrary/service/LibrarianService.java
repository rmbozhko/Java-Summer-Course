package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;

public interface LibrarianService {

    LibrarianDto findByUsername(String username) throws FoundNoInstanceException;
}
