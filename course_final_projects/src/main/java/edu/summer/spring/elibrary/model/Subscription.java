package edu.summer.spring.elibrary.model;

import edu.summer.spring.elibrary.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Subscriptions")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString(exclude = {"id"})
public class Subscription {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

    @NonNull
    private String  token;
}
