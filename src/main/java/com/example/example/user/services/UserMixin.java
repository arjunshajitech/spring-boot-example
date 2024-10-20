package com.example.example.user.services;

import com.example.example.user.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.UUID;

public abstract class UserMixin {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    @JsonIgnore
    private String password;

    @JsonProperty("role")
    private Role role;

    @JsonSetter("password")
    public abstract void setPassword(String password);
}
