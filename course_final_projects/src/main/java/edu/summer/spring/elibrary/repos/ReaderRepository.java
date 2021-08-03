package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.Reader;
import edu.summer.spring.elibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByUser(User user);
}
