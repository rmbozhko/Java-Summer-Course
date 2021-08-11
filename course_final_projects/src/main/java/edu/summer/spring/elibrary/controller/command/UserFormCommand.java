package edu.summer.spring.elibrary.controller.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserFormCommand {
    @Pattern(regexp = "[a-zA-Z]{1,20}",
            message = "Not valid username")
    String username;

    @NotBlank
    String password;

    @Pattern(regexp = "[a-zA-Z]")
    String firstName;

    @Pattern(regexp = "[a-zA-Z]")
    String lastName;

    @Email
    String email;
}
