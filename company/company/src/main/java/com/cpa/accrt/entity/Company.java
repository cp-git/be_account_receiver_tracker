package com.cpa.accrt.entity;

import java.security.Identity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="company")

public class Company {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="company_id")
private long companyId;

@Column(name="company_name")
private String companyName;

@Column(name="company_address")
private String companyAddress;

@Column(name="company_person")
private String companyPerson;

@Column(name="company_contact")
private String companyContact;

@Column(name="active")
private boolean active;

/**
 * @return the companyId
 */
public long getCompanyId() {
	return companyId;
}

/**
 * @param companyId the companyId to set
 */
public void setCompanyId(long companyId) {
	this.companyId = companyId;
}

/**
 * @return the companyName
 */
public String getCompanyName() {
	return companyName;
}

/**
 * @param companyName the companyName to set
 */
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

/**
 * @return the companyAddress
 */
public String getCompanyAddress() {
	return companyAddress;
}

/**
 * @param companyAddress the companyAddress to set
 */
public void setCompanyAddress(String companyAddress) {
	this.companyAddress = companyAddress;
}

/**
 * @return the companyPerson
 */
public String getCompanyPerson() {
	return companyPerson;
}

/**
 * @param companyPerson the companyPerson to set
 */
public void setCompanyPerson(String companyPerson) {
	this.companyPerson = companyPerson;
}

/**
 * @return the companyContact
 */
public String getCompanyContact() {
	return companyContact;
}

/**
 * @param companyContact the companyContact to set
 */
public void setCompanyContact(String companyContact) {
	this.companyContact = companyContact;
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
 * @param companyId
 * @param companyName
 * @param companyAddress
 * @param companyPerson
 * @param companyContact
 * @param active
 */
public Company(long companyId, String companyName, String companyAddress, String companyPerson, String companyContact,
		boolean active) {
	super();
	this.companyId = companyId;
	this.companyName = companyName;
	this.companyAddress = companyAddress;
	this.companyPerson = companyPerson;
	this.companyContact = companyContact;
	this.active = active;
}

/**
 * 
 */
public Company() {
	super();
}

@Override
public String toString() {
	return "Company [companyId=" + companyId + ", companyName=" + companyName + ", companyAddress=" + companyAddress
			+ ", companyPerson=" + companyPerson + ", companyContact=" + companyContact + ", active=" + active + "]";
}


}