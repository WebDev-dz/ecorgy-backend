package com.example.app.security;

import com.example.app.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String email, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        String role = "ROLE_USER"; // Default
        // Determine role based on class type
        // Use a cleaner way if discriminator value is available, but for now checking
        // instance
        String className = user.getClass().getSimpleName().toUpperCase();
        // e.g. CLIENT, ADMIN, SELLER, WORKER
        // Note: User itself is abstract, so it must be one of the subclasses

        // Sometimes Hibernate returns a proxy, so simple name might be wrong if not
        // unproxied.
        // Assuming joined/single table strategy, simpler checking:
        if (user.getClass().getSimpleName().contains("Admin"))
            role = "ROLE_ADMIN";
        else if (user.getClass().getSimpleName().contains("Client"))
            role = "ROLE_CLIENT";
        else if (user.getClass().getSimpleName().contains("Seller"))
            role = "ROLE_SELLER";
        else if (user.getClass().getSimpleName().contains("Worker"))
            role = "ROLE_WORKER";

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // We use email as username
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
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
