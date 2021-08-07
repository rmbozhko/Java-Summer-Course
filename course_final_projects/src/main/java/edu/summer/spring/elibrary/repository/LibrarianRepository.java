package edu.summer.spring.elibrary.repository;

import edu.summer.spring.elibrary.model.Librarian;
import edu.summer.spring.elibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian,Long> {

    Optional<Librarian> findByPresentIsTrue();
    Optional<Librarian> findByUser(User user);
}
