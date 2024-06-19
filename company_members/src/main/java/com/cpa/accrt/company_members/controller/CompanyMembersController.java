package com.cpa.accrt.company_members.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpa.accrt.company_members.dto.CompanyMembersDTO;
import com.cpa.accrt.company_members.entity.CompanyMembers;
import com.cpa.accrt.company_members.service.CompanyMembersService;

@RestController
@RequestMapping("/api/companyMembers")
public class CompanyMembersController {

	
	@Autowired
    private  CompanyMembersService companyMembersService;



    @PostMapping("/saveCompanyMember")
    public ResponseEntity<String> saveCompanyMember(@RequestBody CompanyMembersDTO companyMembersDTO) {
        companyMembersService.saveCompanyMember(companyMembersDTO); // Delegate saving logic to service

        // Optionally, you can return a success message or object if needed
        return ResponseEntity.status(HttpStatus.CREATED).body("Company member saved successfully");
    }
    
    @GetMapping("/{memberId}")
    public ResponseEntity<CompanyMembers> getCompanyMemberById(@PathVariable Integer memberId) {
        return companyMembersService.getCompanyMemberById(memberId)
                .map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<CompanyMembers>> getAllCompanyMembers() {
        List<CompanyMembers> members = companyMembersService.getAllCompanyMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<CompanyMembers> updateCompanyMember(@PathVariable Integer memberId, @RequestBody CompanyMembers updatedMember) {
        CompanyMembers updated = companyMembersService.updateCompanyMember(memberId, updatedMember);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteCompanyMember(@PathVariable Integer memberId) {
        if (companyMembersService.existsById(memberId)) {
            companyMembersService.deleteCompanyMember(memberId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
