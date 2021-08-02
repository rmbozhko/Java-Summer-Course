package edu.summer.spring.elibrary.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

public enum Role implements GrantedAuthority {
    READER,
    LIBRARIAN,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
