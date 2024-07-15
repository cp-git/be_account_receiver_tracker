package com.cpa.accrt.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpa.accrt.entity.LoginDetails;

@Repository
public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Integer> {
	   Optional<LoginDetails> findByUsername(String username);
}