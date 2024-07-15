package com.cpa.accrt.company_members.dto;

public class CompanyDTO {
    private Long companyId;

    // Constructors
    public CompanyDTO() {
    }

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "CompanyDTO [companyId=" + companyId + "]";
	}

   
    
    
}
