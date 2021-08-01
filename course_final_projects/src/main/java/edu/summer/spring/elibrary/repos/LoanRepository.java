package edu.summer.spring.elibrary.repos;

import edu.summer.spring.elibrary.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    long    deleteLoansByBook_Id(Integer book_id);
}
