package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.User;

import java.util.Map;

public interface ReaderService {
    ReaderDto   signup(ReaderDto readerDto);
    ReaderDto   findByUsername(String username) throws FoundNoInstanceException;

    Map<String, User> getAllReadersData();
}
