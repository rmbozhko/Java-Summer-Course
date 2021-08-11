package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.model.Librarian;
import org.springframework.stereotype.Component;

@Component
public class LibrarianMapper {
    public static LibrarianDto toLibrarianDto(Librarian librarian) {
        return LibrarianDto.builder()
                            .username(librarian.getUser().getUsername())
                            .password(librarian.getUser().getPassword())
                            .firstName(librarian.getUser().getFirstName())
                            .lastName(librarian.getUser().getLastName())
                            .email(librarian.getUser().getEmail())
                            .build();
    }
}
