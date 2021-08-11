package edu.summer.spring.elibrary.dto.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Builder
public class LibrarianDto {
    private String      username;
    private String      password;
    private String      firstName;
    private String      lastName;
    private String      email;
}