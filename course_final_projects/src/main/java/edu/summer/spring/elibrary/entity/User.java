package edu.summer.spring.elibrary.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "Users")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@ToString(exclude = {"id"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer     id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String      username;

    @NonNull
    @Column(nullable = false)
    private String      password;

    @Column(columnDefinition = "boolean default true", nullable = true)
    private boolean      active;

    @Enumerated(EnumType.ORDINAL)
    private Role        role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(getRole());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
