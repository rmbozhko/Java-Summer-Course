package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User    findByUsername(String username);
}
