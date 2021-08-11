package edu.summer.spring.elibrary.dto.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
//@NoArgsConstructor
@ToString
//@Accessors(chain = true)
public class ReaderDto {
    private String      username;
    private String      password;
    private String      firstName;
    private String      lastName;
    private String      email;
}
