package edu.summer.spring.elibrary.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class BookDto {
    private String     title;
    private String     author;
    private String     publisher;
    private LocalDate  publishingDate;
    private String     ISBN;
}
