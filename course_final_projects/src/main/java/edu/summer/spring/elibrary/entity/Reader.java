package edu.summer.spring.elibrary.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Readers")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id"})
public class Reader {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer           id;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User    user;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Subscription    subscription;
}
