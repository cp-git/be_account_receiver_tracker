package com.cpa.accrt.company_members.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "company_members")
public class CompanyMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Member email is required")
    @Column(name = "member_email", unique = true, nullable = false)
    private String memberEmail;

    @NotBlank(message = "Member contact is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Column(name = "member_contact", unique = true, nullable = false)
    private String memberContact;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Company company;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_details_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

    private LoginDetails loginDetails; // Assuming LoginDetails is another entity

    @Column(name = "active")
    private boolean active;

    // Getters and setters

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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
        this.memberContact = memberContact.replaceAll("\\D", "");

    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

	/**
	 * @return the loginDetails
	 */
	public LoginDetails getLoginDetails() {
		return loginDetails;
	}

	/**
	 * @param loginDetails the loginDetails to set
	 */
	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}

	public CompanyMembers(int memberId, String memberName, String memberEmail,
			@Valid @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be exactly 10 digits") @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits") String memberContact,
			Company company, LoginDetails loginDetails, boolean active) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.memberContact = memberContact;
		this.company = company;
		this.loginDetails = loginDetails;
		this.active = active;
	}

	public CompanyMembers() {
		super();
	}

    
}
