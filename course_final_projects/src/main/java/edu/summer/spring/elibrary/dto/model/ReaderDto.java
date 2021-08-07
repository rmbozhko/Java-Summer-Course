package edu.summer.spring.elibrary.dto.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ReaderDto {
    private String      username;
    private String      firstName;
    private String      lastName;
    private String      email;
}
