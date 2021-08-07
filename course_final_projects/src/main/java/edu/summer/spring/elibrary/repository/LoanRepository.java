package edu.summer.spring.elibrary.repository;

import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.Librarian;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllBySubscription(Subscription subscription);
    List<Loan> findAllByLibrarian(Librarian librarian);

    long deleteLoansByBook(Book book);

    boolean existsLoanByBookAndSubscription(Book book, Subscription subscription);
}
