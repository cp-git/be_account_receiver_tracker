package com.cpa.accrt.company_members.dto;


import javax.validation.constraints.NotNull;

public class LoginDetailsDTO {
    private int id;

    @NotNull
    private String username;
    
    @NotNull
    private String password;

	@NotNull
    private RoleDTO role;

    @NotNull
    private boolean active;
    
    @NotNull
    private boolean isloggedIn;
    /**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



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

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    // Constructors
    public LoginDetailsDTO() {}

	/**
	 * @return the isloggedIn
	 */
	public boolean isIsloggedIn() {
		return isloggedIn;
	}

	/**
	 * @param isloggedIn the isloggedIn to set
	 */
	public void setIsloggedIn(boolean isloggedIn) {
		this.isloggedIn = isloggedIn;
	}

	public LoginDetailsDTO(int id, @NotNull String username, @NotNull String password, @NotNull RoleDTO role,
			@NotNull boolean active, @NotNull boolean isloggedIn) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.active = active;
		this.isloggedIn = isloggedIn;
	}


}

