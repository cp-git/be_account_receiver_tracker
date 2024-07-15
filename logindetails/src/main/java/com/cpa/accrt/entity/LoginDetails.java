package com.cpa.accrt.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class LoginDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true, nullable=false)
    private String username;

    @Column(name="password",nullable=false)
    private String password;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @NotNull
    @Column(nullable=false)
    private boolean active;
    
    @NotNull
    @Column(name="is_loggedin", nullable=false)
    private boolean isLoggedIn;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    /**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
	/**
	 * @return the isLoggenIn
	 */
	

    public LoginDetails(String username, String password, Role role, boolean active, boolean isLoggedIn) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
        this.isLoggedIn = isLoggedIn;
    }

	/**
	 * @return the isLoggedIn
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * @param isLoggedIn the isLoggedIn to set
	 */
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	@Override
	public String toString() {
		return "LoginDetails [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", active=" + active + "]";
	}

	public LoginDetails() {
		super();
	}
    
    
    
}