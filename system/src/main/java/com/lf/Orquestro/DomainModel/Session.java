package com.lf.Orquestro.DomainModel;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "session_token", nullable = false, unique = true)
    private UUID sessionToken;

    @Column(name = "logout_time")
    private LocalDateTime logoutTime;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Session() {
        super();
    }

    public Session(User user, String ipAddress, String userAgent) {
        super();
        this.sessionToken = UUID.randomUUID();
        this.user = user;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    // Getters e Setters
    public UUID getSessionToken() { return sessionToken; }
    public void setSessionToken(UUID sessionToken) { this.sessionToken = sessionToken; }
    public LocalDateTime getLogoutTime() { return logoutTime; }
    public void setLogoutTime(LocalDateTime logoutTime) { this.logoutTime = logoutTime; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getLastActivity() { return getUpdatedAt(); }
    public void setLastActivity(LocalDateTime lastActivity) { setUpdatedAt(lastActivity); }


    @Override
    public int hashCode() {
        return Objects.hash(sessionToken);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Session other = (Session) obj;
        return Objects.equals(sessionToken, other.sessionToken);
    }
}