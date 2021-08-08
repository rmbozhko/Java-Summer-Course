package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Loan> updatePenalty(List<Loan> loans) {
        for (Loan loan : loans) {
            long daysBetween = DAYS.between(LocalDate.now(), loan.getEndDate());
            if (daysBetween < 0) {
                loan.setPenalty(Loan.DAILY_PENALTY_HRV * Math.abs(daysBetween));
            }
            loanRepository.save(loan);
        }
        return loans;
    }
}
