package edu.summer.spring.elibrary.model;

import edu.summer.spring.elibrary.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Librarians")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@ToString(exclude = {"id"})
public class Librarian {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer     id;

    @NonNull
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean     present;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User user;
}
