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

@Column
private String member_name;


@Column
private String member_email;


@Column
private String member_contact;

@ManyToOne(fetch = FetchType.LAZY) // Adjust fetch type as needed
@JoinColumn(name = "company_id", nullable = false) // name should match column name in DB
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

private Company company;
@Column
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
 * @return the member_name
 */
public String getMember_name() {
	return member_name;
}

/**
 * @param member_name the member_name to set
 */
public void setMember_name(String member_name) {
	this.member_name = member_name;
}

/**
 * @return the member_email
 */
public String getMember_email() {
	return member_email;
}

/**
 * @param member_email the member_email to set
 */
public void setMember_email(String member_email) {
	this.member_email = member_email;
}

/**
 * @return the member_contact
 */
public String getMember_contact() {
	return member_contact;
}

/**
 * @param member_contact the member_contact to set
 */
public void setMember_contact(String member_contact) {
	this.member_contact = member_contact;
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
 * @param member_name
 * @param member_email
 * @param member_contact
 * @param company_id
 * @param active
 */


/**
 * 
 */
public CompanyMembers() {
	super();
}

/**
 * @param memberId
 * @param member_name
 * @param member_email
 * @param member_contact
 * @param company
 * @param active
 */
public CompanyMembers(int memberId, String member_name, String member_email, String member_contact, Company company,
		boolean active) {
	super();
	this.memberId = memberId;
	this.member_name = member_name;
	this.member_email = member_email;
	this.member_contact = member_contact;
	this.company = company;
	this.active = active;
}

@Override
public String toString() {
	return "CompanyMembers [memberId=" + memberId + ", member_name=" + member_name + ", member_email="
			+ member_email + ", member_contact=" + member_contact + ", company_id=" + company + ", active="
			+ active + "]";
}
}
