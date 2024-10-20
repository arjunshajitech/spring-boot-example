package com.example.example.user.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public abstract class UserDetailsMixin {

    @JsonIgnore
    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    @JsonIgnore
    public abstract String getPassword();

    @JsonIgnore
    public abstract String getUsername();

    @JsonIgnore
    public abstract boolean isAccountNonExpired();

    @JsonIgnore
    public abstract boolean isAccountNonLocked();

    @JsonIgnore
    public abstract boolean isCredentialsNonExpired();

    @JsonIgnore
    public abstract boolean isEnabled();
}
