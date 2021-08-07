package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto findByISBN(String isbn) throws FoundNoInstanceException {
        Book book = bookRepository.findBookByISBN(isbn)
                                    .orElseThrow(
                                            () -> new FoundNoInstanceException("No book with specified ISBN was found."));
        return new BookDto().setTitle(book.getTitle())
                            .setAuthor(book.getAuthor())
                            .setPublisher(book.getPublisher())
                            .setPublishingDate(book.getPublishingDate().toString())
                            .setISBN(isbn)
                            .setQuantity(book.getQuantity());
    }
}
