package ua.korzh.testtask.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Set.of(Authority.READ)),
    ADMIN(Set.of(Authority.WRITE));

    private final Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {

        return this.authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());
    }
}
