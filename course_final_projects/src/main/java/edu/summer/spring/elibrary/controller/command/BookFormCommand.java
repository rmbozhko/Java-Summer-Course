package edu.summer.spring.elibrary.controller.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

interface OnCreate {}

interface OnUpdate {}

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BookFormCommand {
    @Null(groups = OnUpdate.class)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String author;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String publisher;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishingDate;

    @ISBN
    private String ISBN;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @PositiveOrZero
    private Integer quantity;
}
