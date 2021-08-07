package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.model.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {
    public static ReaderDto toReaderDto(Reader reader) {
        return new ReaderDto().setUsername(reader.getUser().getUsername())
                            .setFirstName(reader.getUser().getFirstName())
                            .setLastName(reader.getUser().getLastName())
                            .setEmail(reader.getUser().getEmail());
    }
}
