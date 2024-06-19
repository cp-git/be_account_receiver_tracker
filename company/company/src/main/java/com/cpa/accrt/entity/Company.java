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
private long companyId;

@Column
private String company_name;

@Column
private String company_address;

@Column
private String company_person;

@Column
private String company_contact;

@Column
private boolean active;

/**

@return the active
*/
public boolean isActive() {
return active;
}
/**

@param active the active to set
*/
public void setActive(boolean active) {
this.active = active;
}
/**

@return the companyId
*/
public long getCompanyId() {
return companyId;
}
/**

@param companyId the companyId to set
*/
public void setCompanyId(long companyId) {
this.companyId = companyId;
}
/**

@return the company_name
*/
public String getCompany_name() {
return company_name;
}
/**

@param company_name the company_name to set
*/
public void setCompany_name(String company_name) {
this.company_name = company_name;
}
/**

@return the company_address
*/
public String getCompany_address() {
return company_address;
}
/**

@param company_address the company_address to set
*/
public void setCompany_address(String company_address) {
this.company_address = company_address;
}
/**

@return the company_ceo
*/
public String getCompany_ceo() {
return company_person;
}
/**

@param company_ceo the company_ceo to set
*/
public void setCompany_ceo(String company_person) {
this.company_person = company_person;
}
/**

@return the company_contact
*/
public String getCompany_contact() {
return company_contact;
}
/**

@param company_contact the company_contact to set
*/
public void setCompany_contact(String company_contact) {
this.company_contact = company_contact;
}
/**

@param companyId
@param company_name
@param company_address
@param company_ceo
@param company_contact
@param active
*/
public Company(long companyId, String company_name, String company_address, String company_person, String company_contact,
boolean active) {
super();
this.companyId = companyId;
this.company_name = company_name;
this.company_address = company_address;
this.company_person = company_person;
this.company_contact = company_contact;
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
return "Company [companyId=" + companyId + ", company_name=" + company_name + ", company_address=" + company_address
+ ", company_ceo=" + company_person + ", company_contact=" + company_contact + ", active=" + active + "]";
}

}