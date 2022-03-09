package com.challenge.vendingmachine.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class UserSession {

    @Id
    private Long userId;

    private String username;

    private String currentToken;

    private Date sessionExpiration;

    private boolean active;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public Date getSessionExpiration() {
        return sessionExpiration;
    }

    public void setSessionExpiration(Date sessionExpiration) {
        this.sessionExpiration = sessionExpiration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "username='" + username + '\'' +
                '}';
    }
}
