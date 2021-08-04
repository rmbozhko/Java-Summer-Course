package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.Book;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book>      findAllByTitleOrAuthorOrISBN(@NonNull String title, @NonNull String author, @NonNull String ISBN);
    Optional<Book>  findById(Integer id);
    Optional<Book>  findBookByISBN(String ISBN);

    List<Book>  findAllByOrderByTitleAsc();
    List<Book>  findAllByOrderByAuthorAsc();
    List<Book>  findAllByOrderByPublisherAsc();
    List<Book>  findAllByOrderByPublishingDateDesc();
}
