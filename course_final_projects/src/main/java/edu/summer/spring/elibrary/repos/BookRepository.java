package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthor(String author);
    List<Book>  findByTitle(String title);
    Optional<Book>  findById(Integer id);
    Optional<Book>  findBookByISBN(String ISBN);

    List<Book>  findAllByOrderByTitleAsc();
    List<Book>  findAllByOrderByAuthorAsc();
    List<Book>  findAllByOrderByPublisherAsc();
    List<Book>  findAllByOrderByPublishingDateDesc();

    long        deleteBookByISBN(String ISBN);
}
