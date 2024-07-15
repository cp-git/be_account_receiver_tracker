package com.cpa.accrt.company_members.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpa.accrt.company_members.entity.LoginDetails;
import com.cpa.accrt.company_members.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
