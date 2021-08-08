package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Loan;

import java.util.List;

public interface LibrarianService {

    LibrarianDto findByUsername(String username) throws FoundNoInstanceException;

    LibrarianDto updatePresence(LibrarianDto librarianDto, Boolean present);

    List<Loan> getLoansOfLibrarian(LibrarianDto librarianDto);
}
