package edu.summer.spring.elibrary.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserSignupRequest {
    private String username;
    String password;
    String firstName;
    String lastName;
    String email;
}
