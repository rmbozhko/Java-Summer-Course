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
public class BookDto {
    private String      title;
    private String      author;
    private String      publisher;
    private String      publishingDate;
    private String      ISBN;
    private Integer     quantity;
}
