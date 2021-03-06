package edu.summer.spring.elibrary.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString(exclude = {"id"})
@Accessors(chain = true)
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
