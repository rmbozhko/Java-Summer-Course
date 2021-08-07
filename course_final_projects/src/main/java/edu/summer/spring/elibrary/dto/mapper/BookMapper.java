package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public static BookDto   toBookDto(Book book) {
        return new BookDto().setTitle(book.getTitle())
                            .setAuthor(book.getAuthor())
                            .setPublisher(book.getPublisher())
                            .setPublishingDate(book.getPublishingDate())
                            .setISBN(book.getISBN());
    }
}
