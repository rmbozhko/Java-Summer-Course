package edu.summer.spring.elibrary.repository;

import edu.summer.spring.elibrary.model.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book>      findAllByTitleOrAuthorOrISBN(String title, String author, String ISBN);
    Optional<Book>  findById(Integer id);
    Optional<Book>  findBookByISBN(String ISBN);

    List<Book>  findAllByOrderByTitleAsc();
    List<Book>  findAllByOrderByAuthorAsc();
    List<Book>  findAllByOrderByPublisherAsc();
    List<Book>  findAllByOrderByPublishingDateDesc();
}
