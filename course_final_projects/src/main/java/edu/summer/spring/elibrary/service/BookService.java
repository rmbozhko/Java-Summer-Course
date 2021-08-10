package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.LoanDuplicateException;
import edu.summer.spring.elibrary.exception.NoFreeBookException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    BookDto findByISBN(String isbn) throws FoundNoInstanceException;
    List<Book> findAllByTitleOrAuthorOrISBN(String title, String author, String isbn);

    List<Book> findAll();

    Page<Book> sortByProperty(String property);

    BookDto findById(Integer id) throws FoundNoInstanceException;

    BookDto orderBookById(Integer id, Integer loanPeriod, User user) throws FoundNoInstanceException, LoanDuplicateException, NoFreeBookException;

    Page<Book> getBooksFromCataloguePage(String page);
}
