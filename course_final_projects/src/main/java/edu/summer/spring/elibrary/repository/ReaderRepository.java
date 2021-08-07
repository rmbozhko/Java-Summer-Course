package edu.summer.spring.elibrary.repository;

import edu.summer.spring.elibrary.model.Reader;
import edu.summer.spring.elibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByUser(User user);
}
