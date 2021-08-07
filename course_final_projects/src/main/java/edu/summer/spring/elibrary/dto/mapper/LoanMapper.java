package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.LoanDto;
import edu.summer.spring.elibrary.model.Loan;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class LoanMapper {
    public static LoanDto   toLoanDto(Loan loan) {
        return new LoanDto().setBookTitle(loan.getBook().getTitle())
                            .setBookAuthor(loan.getBook().getAuthor())
                            .setBookISBN(loan.getBook().getISBN())
                            .setBeginDate(loan.getBeginDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .setEndDate(loan.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .setPenalty(loan.getPenalty());
    }
}
