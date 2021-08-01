package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsSubscriptionByUserId(Integer id);

    Subscription findOneByUserId(Integer id);
}
