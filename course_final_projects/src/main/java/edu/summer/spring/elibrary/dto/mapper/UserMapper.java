package edu.summer.spring.elibrary.dto.mapper;

import edu.summer.spring.elibrary.dto.model.LibrarianDto;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.dto.model.UserDto;
import edu.summer.spring.elibrary.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto().setUsername(user.getUsername())
                            .setPassword(user.getPassword())
                            .setFirstName(user.getFirstName())
                            .setLastName(user.getLastName())
                            .setEmail(user.getEmail());
    }

    public static User toUser(LibrarianDto librarianDto) {
        return new User(librarianDto.getUsername(),
                librarianDto.getPassword(),
                librarianDto.getFirstName(),
                librarianDto.getLastName(),
                librarianDto.getEmail());
    }

    public static User toUser(ReaderDto readerDto) {
        return new User(readerDto.getUsername(), readerDto.getPassword(),
                readerDto.getFirstName(), readerDto.getLastName(),
                readerDto.getEmail());
    }
}
