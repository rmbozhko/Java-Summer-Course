package edu.summer.spring.elibrary.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Readers")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reader extends User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer           id;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Subscription    subscription;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User    user;
}
