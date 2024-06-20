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
        companyMembers.setMemberName(companyMembersDTO.getMemberName());
        companyMembers.setMemberEmail(companyMembersDTO.getMemberEmail());
        companyMembers.setMemberContact(companyMembersDTO.getMemberContact());
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

//    @Transactional
//
//    public CompanyMembers updateCompanyMember(Integer memberId, CompanyMembers updatedMember) {
//        Optional<CompanyMembers> optionalMember = companyMembersRepository.findById(memberId);
//        if (optionalMember.isPresent()) {
//            CompanyMembers existingMember = optionalMember.get();
//            existingMember.setMember_name(updatedMember.getMember_name());
//            existingMember.setMember_email(updatedMember.getMember_email());
//            existingMember.setMember_contact(updatedMember.getMember_contact());
//            existingMember.setCompany(updatedMember.getCompany());
//            existingMember.setActive(updatedMember.isActive());
//            return companyMembersRepository.save(existingMember);
//        }
//        return null; // Or throw an exception indicating the member was not found
//    }
    @Transactional
    public CompanyMembers updateCompanyMember(Integer memberId, CompanyMembers updatedMember) {
        Optional<CompanyMembers> optionalMember = companyMembersRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            CompanyMembers existingMember = optionalMember.get();

            // Check if the Company is transient
            Company updatedCompany = updatedMember.getCompany();

            Company companyObj = companyRepository.findByCompanyId(updatedMember.getCompany().getCompanyId());

            if (updatedCompany != null) {
                // Check if the company exists in the database
                if (!companyRepository.existsById(updatedCompany.getCompanyId())) {
                    // Ensure all required fields are set before saving
                    if (updatedCompany.getCompanyContact() == null) {
                        throw new IllegalArgumentException("Company contact cannot be null");
                    }
                    // Copy fields from existing company object
                    updatedCompany.setCompanyName(companyObj.getCompanyName());
                    updatedCompany.setCompanyAddress(companyObj.getCompanyAddress());
                    updatedCompany.setCompanyPerson(companyObj.getCompanyPerson());
                    updatedCompany.setCompanyContact(companyObj.getCompanyContact());
                    updatedCompany.setActive(companyObj.isActive());
                    System.out.println("Updated Company after setting values: " + updatedCompany);

                    // Save the Company entity first if it does not exist
                    updatedCompany = companyRepository.save(updatedCompany);
                }
            }

            // Update member details
            existingMember.setMemberName(updatedMember.getMemberName());
            existingMember.setMemberEmail(updatedMember.getMemberEmail());
            existingMember.setMemberContact(updatedMember.getMemberContact());
            existingMember.setCompany(updatedCompany); // Set the updated company
            existingMember.setActive(updatedMember.isActive());

            // Save and return updated member
            return companyMembersRepository.save(existingMember);
        }

        return null; // Or throw an exception indicating the member was not found
    }

}

