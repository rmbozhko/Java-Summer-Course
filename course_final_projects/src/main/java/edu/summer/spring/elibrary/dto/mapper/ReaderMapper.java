package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.model.Reader;
import edu.summer.spring.elibrary.model.User;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {
    public static ReaderDto toReaderDto(Reader reader) {
        return ReaderDto.builder()
                .username(reader.getUser().getUsername())
                .password(reader.getUser().getPassword())
                .firstName(reader.getUser().getFirstName())
                .lastName(reader.getUser().getLastName())
                .email(reader.getUser().getEmail())
                .build();
    }

    public static ReaderDto toReaderDto(User user) {
        return ReaderDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
