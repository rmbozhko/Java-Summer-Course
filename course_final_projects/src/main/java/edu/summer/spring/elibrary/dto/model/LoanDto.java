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
public class LoanDto {
    private String      bookTitle;
    private String      bookAuthor;
    private String      bookISBN;
    private String      beginDate;
    private String      endDate;
    private Double      penalty;
}
