package edu.summer.spring.elibrary.controller.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserFormCommand {
    private String username;
    String password;
    String firstName;
    String lastName;
    String email;
}
