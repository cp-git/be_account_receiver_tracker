package com.cpa.accrt.company_members.service;

import com.cpa.accrt.company_members.dto.CompanyMembersDTO;
import com.cpa.accrt.company_members.entity.Company;
import com.cpa.accrt.company_members.entity.CompanyMembers;
import com.cpa.accrt.company_members.entity.LoginDetails;
import com.cpa.accrt.company_members.entity.Role;
import com.cpa.accrt.company_members.repository.CompanyMembersRepository;
import com.cpa.accrt.company_members.repository.CompanyRepository;
import com.cpa.accrt.company_members.repository.LoginDetailsRepository;
import com.cpa.accrt.company_members.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyMembersService {

    private final CompanyRepository companyRepository;
    private final CompanyMembersRepository companyMembersRepository;
    private final LoginDetailsRepository loginDetailsRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public CompanyMembersService(CompanyRepository companyRepository, CompanyMembersRepository companyMembersRepository,
                                 LoginDetailsRepository loginDetailsRepository, RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.companyMembersRepository = companyMembersRepository;
        this.loginDetailsRepository = loginDetailsRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void saveCompanyMember(CompanyMembersDTO companyMembersDTO) {
        Company company = companyRepository.findById(companyMembersDTO.getCompany().getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company with id " + companyMembersDTO.getCompany().getCompanyId() + " not found"));

        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(companyMembersDTO.getLoginDetails().getUsername());
        loginDetails.setPassword(companyMembersDTO.getLoginDetails().getPassword());
        loginDetails.setActive(companyMembersDTO.getLoginDetails().isActive());
        loginDetails.setLoggedIn(companyMembersDTO.getLoginDetails().isIsloggedIn());
     // Fetch or create a Role instance with ID 1 (SUPER_ADMIN), assuming Role is an entity
        Role role = roleRepository.findById(companyMembersDTO.getLoginDetails().getRole().getId()).orElseThrow(() -> new RuntimeException("Role not found")); // Replace with your repository

        role = roleRepository.save(role);
        loginDetails.setRole(role);
        loginDetails = loginDetailsRepository.save(loginDetails);

        CompanyMembers companyMembers = new CompanyMembers();
        companyMembers.setMemberName(companyMembersDTO.getMemberName());
        companyMembers.setMemberEmail(companyMembersDTO.getMemberEmail());
        companyMembers.setMemberContact(companyMembersDTO.getMemberContact());
        companyMembers.setActive(companyMembersDTO.isActive());
        companyMembers.setCompany(company);
        companyMembers.setLoginDetails(loginDetails);
        companyMembersRepository.save(companyMembers);
    }

    public Optional<CompanyMembers> getCompanyMemberById(Integer memberId) {
        return companyMembersRepository.findById(memberId);
    }

    public List<CompanyMembers> getAllCompanyMembers() {
        return companyMembersRepository.findAll();
    }

    @Transactional
    public CompanyMembers updateCompanyMember(Integer memberId, CompanyMembers updatedMember) {
        Optional<CompanyMembers> optionalMember = companyMembersRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            CompanyMembers existingMember = optionalMember.get();

            Company updatedCompany = companyRepository.findById(updatedMember.getCompany().getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("Company with id " + updatedMember.getCompany().getCompanyId() + " not found"));

            existingMember.setMemberName(updatedMember.getMemberName());
            existingMember.setMemberEmail(updatedMember.getMemberEmail());
            existingMember.setMemberContact(updatedMember.getMemberContact());
            existingMember.setCompany(updatedCompany);
            existingMember.setActive(updatedMember.isActive());

            if (updatedMember.getLoginDetails() != null) {
                LoginDetails updatedLoginDetails = updatedMember.getLoginDetails();
                LoginDetails existingLoginDetails = existingMember.getLoginDetails();

                existingLoginDetails.setUsername(updatedLoginDetails.getUsername());
                existingLoginDetails.setPassword(updatedLoginDetails.getPassword());
                existingLoginDetails.setActive(updatedLoginDetails.isActive());
                existingLoginDetails.setLoggedIn(updatedLoginDetails.isLoggedIn());

                if (updatedLoginDetails.getRole() != null) {
                    Role updatedRole = roleRepository.findById(updatedLoginDetails.getRole().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Role with id " + updatedLoginDetails.getRole().getId() + " not found"));
                    existingLoginDetails.setRole(updatedRole);
                }

                loginDetailsRepository.save(existingLoginDetails);
            }

            return companyMembersRepository.save(existingMember);
        }

        return null;
    }


    @Transactional
    public void deleteCompanyMember(Integer memberId) {
        CompanyMembers companyMember = companyMembersRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Company member with id " + memberId + " not found"));

        LoginDetails loginDetails = companyMember.getLoginDetails();

        companyMembersRepository.deleteById(memberId);

        if (loginDetails != null) {
            loginDetailsRepository.deleteById(loginDetails.getId());
        }
    }

    // Other service methods...


    public boolean existsById(Integer memberId) {
        return companyMembersRepository.existsById(memberId);
    }
    
    public CompanyMembers getCompanyMembersByLoginDetailsId(int loginDetailsId) {
        return companyMembersRepository.findByLoginDetails_Id(loginDetailsId);
    }
}
