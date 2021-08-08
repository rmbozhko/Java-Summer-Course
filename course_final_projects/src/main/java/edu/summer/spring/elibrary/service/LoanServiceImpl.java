package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.UserDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Loan;
import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.LoanRepository;
import edu.summer.spring.elibrary.repository.SubscriptionRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

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

    @Override
    public List<Loan> findAllByUser(UserDto userDto) throws FoundNoInstanceException {
        User reader = userRepository.findByUsername(userDto.getUsername()).orElseThrow(
                        () -> new FoundNoInstanceException("No user found with specified username"));
        Subscription subscription = subscriptionRepository.findByUser(reader).orElseThrow(
                        () -> new FoundNoInstanceException("Provided username doesn't belong to reader."));
        return loanRepository.findAllBySubscription(subscription);
    }
}
