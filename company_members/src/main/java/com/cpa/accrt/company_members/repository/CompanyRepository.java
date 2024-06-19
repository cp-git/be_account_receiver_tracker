package com.cpa.accrt.company_members.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpa.accrt.company_members.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
