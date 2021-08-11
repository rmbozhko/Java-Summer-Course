package edu.summer.spring.elibrary.dto.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class BookDto {
    @NonNull private String      title;
    @NonNull private String      author;
    @NonNull private String      publisher;
    @NonNull private String      publishingDate;
    @NonNull private String      ISBN;
    @NonNull private Integer     quantity;
}
