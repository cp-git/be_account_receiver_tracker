package com.cpa.accrt.company_members.dto;

import jakarta.validation.constraints.NotNull;

public class CompanyMembersDTO {

	 private int id;

	    @NotNull
	    private String memberName;

	    @NotNull
	    private String memberEmail;

	    @NotNull
	    private String memberContact;

	    @NotNull
	    private CompanyDTO company;

	    @NotNull
	    private LoginDetailsDTO loginDetails;

	    @NotNull
	    private boolean active;

	    // Getters and Setters
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getMemberName() {
	        return memberName;
	    }

	    public void setMemberName(String memberName) {
	        this.memberName = memberName;
	    }

	    public String getMemberEmail() {
	        return memberEmail;
	    }

	    public void setMemberEmail(String memberEmail) {
	        this.memberEmail = memberEmail;
	    }

	    public String getMemberContact() {
	        return memberContact;
	    }

	    public void setMemberContact(String memberContact) {
	        this.memberContact = memberContact;
	    }

	    public CompanyDTO getCompany() {
	        return company;
	    }

	    public void setCompany(CompanyDTO company) {
	        this.company = company;
	    }

	    public LoginDetailsDTO getLoginDetails() {
	        return loginDetails;
	    }

	    public void setLoginDetails(LoginDetailsDTO loginDetails) {
	        this.loginDetails = loginDetails;
	    }

	    public boolean isActive() {
	        return active;
	    }

	    public void setActive(boolean active) {
	        this.active = active;
	    }

	    // Constructors
	    public CompanyMembersDTO() {}

	    public CompanyMembersDTO(int id, @NotNull String memberName, @NotNull String memberEmail, @NotNull String memberContact, @NotNull CompanyDTO company, @NotNull LoginDetailsDTO loginDetails, @NotNull boolean active) {
	        this.id = id;
	        this.memberName = memberName;
	        this.memberEmail = memberEmail;
	        this.memberContact = memberContact;
	        this.company = company;
	        this.loginDetails = loginDetails;
	        this.active = active;
	    }
}
