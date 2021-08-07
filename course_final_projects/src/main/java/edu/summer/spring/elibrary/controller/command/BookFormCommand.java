package edu.summer.spring.elibrary.controller.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BookFormCommand {
    private String title;
    private String author;
    private String publisher;
    private String publishingDate;
    private String ISBN;

    @PositiveOrZero
    private Integer quantity;
}
