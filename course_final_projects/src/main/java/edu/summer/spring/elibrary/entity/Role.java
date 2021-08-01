package edu.summer.spring.elibrary.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    READER,
    SUPERVISOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
