package com.cpa.accrt.company_members.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpa.accrt.company_members.dto.CompanyMembersDTO;
import com.cpa.accrt.company_members.entity.Company;
import com.cpa.accrt.company_members.entity.CompanyMembers;
import com.cpa.accrt.company_members.repository.CompanyMembersRepository;
import com.cpa.accrt.company_members.repository.CompanyRepository;

@Service
public class CompanyMembersService {

    private final CompanyRepository companyRepository;
    private final CompanyMembersRepository companyMembersRepository;

    @Autowired
    public CompanyMembersService(CompanyRepository companyRepository, CompanyMembersRepository companyMembersRepository) {
        this.companyRepository = companyRepository;
        this.companyMembersRepository = companyMembersRepository;
    }

    @Transactional
    public void saveCompanyMember(CompanyMembersDTO companyMembersDTO) {
        // Fetch company from database using company ID in DTO
        Company company = companyRepository.findById(companyMembersDTO.getCompany().getId())
                .orElseThrow(() -> new IllegalArgumentException("Company with id " + companyMembersDTO.getCompany().getId() + " not found"));

        // Map DTO fields to entity fields
        CompanyMembers companyMembers = new CompanyMembers();
        companyMembers.setMember_name(companyMembersDTO.getMemberName());
        companyMembers.setMember_email(companyMembersDTO.getMemberEmail());
        companyMembers.setMember_contact(companyMembersDTO.getMemberContact());
        companyMembers.setCompany(company); // Set the retrieved company

        companyMembersRepository.save(companyMembers);
    }
    
    

    public Optional<CompanyMembers> getCompanyMemberById(Integer memberId) {
        return companyMembersRepository.findById(memberId);
    }

    public List<CompanyMembers> getAllCompanyMembers() {
        return companyMembersRepository.findAll();
    }

    public void deleteCompanyMember(Integer memberId) {
        companyMembersRepository.deleteById(memberId);
    }

    public boolean existsById(Integer memberId) {
        return companyMembersRepository.existsById(memberId);
    }

    public CompanyMembers updateCompanyMember(Integer memberId, CompanyMembers updatedMember) {
        Optional<CompanyMembers> optionalMember = companyMembersRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            CompanyMembers existingMember = optionalMember.get();
            existingMember.setMember_name(updatedMember.getMember_name());
            existingMember.setMember_email(updatedMember.getMember_email());
            existingMember.setMember_contact(updatedMember.getMember_contact());
            existingMember.setCompany(updatedMember.getCompany());
            existingMember.setActive(updatedMember.isActive());
            return companyMembersRepository.save(existingMember);
        }
        return null; // Or throw an exception indicating the member was not found
    }

}

