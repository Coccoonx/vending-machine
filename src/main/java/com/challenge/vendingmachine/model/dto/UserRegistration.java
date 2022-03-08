package com.challenge.vendingmachine.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserRegistration {

    @NotEmpty(message = "Username may not be empty")
    @Size(min = 2, max = 32, message = "Username must be between 2 and 32 characters long")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
