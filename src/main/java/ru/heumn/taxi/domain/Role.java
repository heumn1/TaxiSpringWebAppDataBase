package ru.heumn.taxi.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, DRIVER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
