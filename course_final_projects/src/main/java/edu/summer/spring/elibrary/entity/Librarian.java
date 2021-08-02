package edu.summer.spring.elibrary.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Librarians")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Librarian extends User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer     id;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean     present;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User    user;
}
