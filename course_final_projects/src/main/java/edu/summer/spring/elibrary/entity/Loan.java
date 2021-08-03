package edu.summer.spring.elibrary.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Loans")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@ToString(exclude = {"id"})
public class Loan {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer     id;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Book        book;

    @NonNull
    @Column(nullable = false)
    private LocalDate   beginDate;

    @NonNull
    @Column(nullable = false)
    private LocalDate   endDate;

    @NonNull
    @Column(nullable = false)
    private Double      penalty;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Subscription subscription;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Librarian    librarian;

    public static Double   DAILY_PENALTY_HRV = 30.0;
}
