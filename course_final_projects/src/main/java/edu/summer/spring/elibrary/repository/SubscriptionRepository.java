package edu.summer.spring.elibrary.repository;

import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription>  findOneByUserId(Integer id);
    Optional<Subscription>  findByUser(User user);
}
