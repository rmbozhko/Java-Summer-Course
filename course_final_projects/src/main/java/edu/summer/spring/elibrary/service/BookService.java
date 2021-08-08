package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.User;

public interface BookService {
    BookDto findByISBN(String isbn) throws FoundNoInstanceException;
    Iterable<Book> findAllByTitleOrAuthorOrISBN(String title, String author, String isbn);

    Iterable<Book> findAll();

    Iterable<Book> sortByProperty(String property);

    BookDto findById(Integer id) throws FoundNoInstanceException;

    BookDto orderBookById(Integer id, Integer loanPeriod, User user) throws FoundNoInstanceException;
}
