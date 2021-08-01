package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.Book;
import edu.summer.spring.elibrary.entity.Loan;
import edu.summer.spring.elibrary.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllBySubscription(Subscription subscription);

    long deleteLoansByBook(Book book);

    boolean existsLoanByBookAndSubscription(Book book, Subscription subscription);
}
