package com.cpa.accrt.company_members.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cpa.accrt.company_members.entity.CompanyMembers;

@Repository
public interface CompanyMembersRepository extends JpaRepository<CompanyMembers, Integer> {
    
	  CompanyMembers findByLoginDetails_Id(int loginDetailsId);
    
}
