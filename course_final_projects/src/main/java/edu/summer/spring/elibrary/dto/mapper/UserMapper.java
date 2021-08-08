package edu.summer.spring.elibrary.dto.mapper;

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
}
