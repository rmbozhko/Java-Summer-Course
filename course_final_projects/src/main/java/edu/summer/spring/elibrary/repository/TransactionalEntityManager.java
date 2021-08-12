package edu.summer.spring.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class TransactionalEntityManager {
    @Transactional
    public <T> T saveEntity(JpaRepository<T, Long> repository, T entity) {
        return repository.save(entity);
    }

    @Transactional
    public <T> void deleteEntity(JpaRepository<T, Long> repository, T entity) {
        repository.delete(entity);
    }
}
