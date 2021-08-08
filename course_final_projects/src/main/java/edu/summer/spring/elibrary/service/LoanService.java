package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.UserDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> updatePenalty(List<Loan> loans);
    List<Loan> findAllByUser(UserDto userDto) throws FoundNoInstanceException;
}
