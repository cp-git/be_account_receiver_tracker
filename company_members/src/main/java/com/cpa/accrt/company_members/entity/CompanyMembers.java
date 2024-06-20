package com.cpa.accrt.company_members.entity;

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

@Entity
@Table(name="company_members")
public class CompanyMembers {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int memberId;

@Column(name="member_name")
private String memberName;


@Column(name="member_email")
private String memberEmail;


@Column(name="member_contact")
private String memberContact;

@ManyToOne(fetch = FetchType.LAZY) // Adjust fetch type as needed
@JoinColumn(name = "company_id", nullable = false) // name should match column name in DB
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

private Company company;

@Column(name="active")
 private boolean active;

/**
 * @return the memberId
 */
public int getMemberId() {
	return memberId;
}

/**
 * @param memberId the memberId to set
 */
public void setMemberId(int memberId) {
	this.memberId = memberId;
}

/**
 * @return the memberName
 */
public String getMemberName() {
	return memberName;
}

/**
 * @param memberName the memberName to set
 */
public void setMemberName(String memberName) {
	this.memberName = memberName;
}

/**
 * @return the memberEmail
 */
public String getMemberEmail() {
	return memberEmail;
}

/**
 * @param memberEmail the memberEmail to set
 */
public void setMemberEmail(String memberEmail) {
	this.memberEmail = memberEmail;
}

/**
 * @return the memberContact
 */
public String getMemberContact() {
	return memberContact;
}

/**
 * @param memberContact the memberContact to set
 */
public void setMemberContact(String memberContact) {
	this.memberContact = memberContact;
}

/**
 * @return the company
 */
public Company getCompany() {
	return company;
}

/**
 * @param company the company to set
 */
public void setCompany(Company company) {
	this.company = company;
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

/**
 * @param memberId
 * @param memberName
 * @param memberEmail
 * @param memberContact
 * @param company
 * @param active
 */
public CompanyMembers(int memberId, String memberName, String memberEmail, String memberContact, Company company,
		boolean active) {
	super();
	this.memberId = memberId;
	this.memberName = memberName;
	this.memberEmail = memberEmail;
	this.memberContact = memberContact;
	this.company = company;
	this.active = active;
}

/**
 * 
 */
public CompanyMembers() {
	super();
}

@Override
public String toString() {
	return "CompanyMembers []";
}

}
