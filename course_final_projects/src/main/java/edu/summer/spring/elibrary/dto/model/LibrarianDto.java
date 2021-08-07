package edu.summer.spring.elibrary.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class LibrarianDto {
    private String      username;
    private String      firstName;
    private String      lastName;
    private String      email;
}