package edu.summer.spring.elibrary.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(force = true)
@ToString(exclude = {"id"})
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer           id;

    @NonNull private String     title;
    @NonNull private String     author;
    @NonNull private String     publisher;
    @NonNull private LocalDate  publishingDate;
    @NonNull private String     ISBN;
    @NonNull private Integer    quantity;
}
