package com.cpa.accrt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpa.accrt.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
