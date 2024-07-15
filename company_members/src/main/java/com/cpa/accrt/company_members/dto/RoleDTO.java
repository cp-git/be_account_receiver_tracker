package com.cpa.accrt.company_members.dto;

import jakarta.validation.constraints.NotNull;

public class RoleDTO {
    private int id;
    @NotNull
    private String roleName;

    // Getters and Setters

   
    public String getRoleName() {
        return roleName;
    }

    /**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
	 // Constructors
    public RoleDTO() {}

    public RoleDTO(int id, @NotNull String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
