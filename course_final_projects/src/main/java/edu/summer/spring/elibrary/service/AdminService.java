package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.dto.model.ReaderDto;

public interface AdminService {
    LibrarianDto addLibrarian(LibrarianDto librarianDto) throws NotUniqueDataException;
    LibrarianDto deleteLibrarian(LibrarianDto librarianDto) throws FoundNoInstanceException;

    String      deActiveReader(ReaderDto readerDto);

    String      addBook(BookDto bookDto);
    String      deleteBook(BookDto bookDto);
    String      updateBook(BookDto bookDto);
}
