package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.model.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> updatePenalty(List<Loan> loans);
}
