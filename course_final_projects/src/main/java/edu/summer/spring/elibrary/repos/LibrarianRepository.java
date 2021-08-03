package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian,Long> {

    Optional<Librarian> findByPresentIsTrue();
}
