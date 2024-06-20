package com.cpa.accrt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cpa.accrt.entity.Company;
import com.cpa.accrt.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(long companyId) {
        return companyRepository.findById(companyId);
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(long companyId, Company companyDetails) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));
        company.setCompanyName(companyDetails.getCompanyName());
        company.setCompanyAddress(companyDetails.getCompanyAddress());
        company.setCompanyPerson(companyDetails.getCompanyPerson());
        company.setCompanyContact(companyDetails.getCompanyContact());
        company.setActive(companyDetails.isActive());
        return companyRepository.save(company);
    }

    public void deleteCompany(long companyId) {
        companyRepository.deleteById(companyId);
    }
}
