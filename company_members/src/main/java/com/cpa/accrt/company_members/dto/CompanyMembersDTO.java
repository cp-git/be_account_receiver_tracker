package com.cpa.accrt.company_members.dto;

public class CompanyMembersDTO {

    private String memberName;
    private String memberEmail;
    private String memberContact;
    private CompanyDTO company;
    private boolean active;

    // Constructors
    public CompanyMembersDTO() {
        this.company = new CompanyDTO(); // Ensure company DTO is initialized
    }

    // Getters and setters for memberName, memberEmail, memberContact, active
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Getters and setters for company
    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }
}
