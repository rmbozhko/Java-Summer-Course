package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.model.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class BookMapper {
    public static BookDto   toBookDto(Book book) {
        return new BookDto().setTitle(book.getTitle())
                        .setAuthor(book.getAuthor())
                        .setPublisher(book.getPublisher())
                        .setPublishingDate(book.getPublishingDate().toString())
                        .setISBN(book.getISBN())
                        .setQuantity(book.getQuantity());
    }

    public static Book toBook(BookDto bookDto) {
        return new Book(bookDto.getTitle(),
                        bookDto.getAuthor(),
                        bookDto.getPublisher(),
                        LocalDate.parse(bookDto.getPublishingDate(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)),
                        bookDto.getISBN(),
                        bookDto.getQuantity());
    }
}
