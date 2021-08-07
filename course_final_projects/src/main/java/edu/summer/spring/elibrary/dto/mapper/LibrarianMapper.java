package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.model.Librarian;
import org.springframework.stereotype.Component;

@Component
public class LibrarianMapper {
    public static LibrarianDto toLibrarianDto(Librarian librarian) {
        return new LibrarianDto().setUsername(librarian.getUser().getUsername())
                                .setPassword(librarian.getUser().getPassword())
                                .setFirstName(librarian.getUser().getFirstName())
                                .setLastName(librarian.getUser().getLastName())
                                .setEmail(librarian.getUser().getEmail());
    }
}
