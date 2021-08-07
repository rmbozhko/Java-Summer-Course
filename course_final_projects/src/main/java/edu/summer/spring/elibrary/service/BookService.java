package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;

public interface BookService {
    BookDto findByISBN(String isbn) throws FoundNoInstanceException;
}
