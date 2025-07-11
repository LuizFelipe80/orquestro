package com.lf.Orquestro.DomainModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "session_token", nullable = false, unique = true)
	private UUID sessionToken;

	@Column(name = "login_time", nullable = false)
	private LocalDateTime loginTime;

	@Column(name = "logout_time")
	private LocalDateTime logoutTime;

	@Column(name = "last_activity", nullable = false)
	private LocalDateTime lastActivity;

	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Column(name = "user_agent")
	private String userAgent;

	@Column(name = "is_active")
	private boolean isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private User account;

	public Session() {
	}

	public Session(User account, String ipAddress, String userAgent) {
		this.sessionToken = UUID.randomUUID();
		this.loginTime = LocalDateTime.now();
		this.lastActivity = LocalDateTime.now();
		this.isActive = true;
		this.account = account;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(UUID sessionToken) {
		this.sessionToken = sessionToken;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public LocalDateTime getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(LocalDateTime logoutTime) {
		this.logoutTime = logoutTime;
	}

	public LocalDateTime getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(LocalDateTime lastActivity) {
		this.lastActivity = lastActivity;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public User getAccount() {
		return account;
	}

	public void setAccount(User account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		return Objects.equals(sessionToken, other.sessionToken);
	}
}