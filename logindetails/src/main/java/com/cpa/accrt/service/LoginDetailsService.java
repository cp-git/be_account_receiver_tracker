package com.cpa.accrt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cpa.accrt.entity.LoginDetails;
import com.cpa.accrt.exception.LoginDetailsNotFoundException;
import com.cpa.accrt.repository.LoginDetailsRepository;



@Service
public class LoginDetailsService {

    @Autowired
    private LoginDetailsRepository loginDetailsRepository;
    
    @Cacheable(value = "loginDetails", key = "#id")
    public Optional<LoginDetails> getLoginDetailsById(int id) {
        Optional<LoginDetails> loginDetails = loginDetailsRepository.findById(id);
        if (!loginDetails.isPresent()) {
            throw new LoginDetailsNotFoundException("LoginDetails not found for this id :: " + id);
        }
        return loginDetails;
    }
    
    @CacheEvict(value = "loginDetails", key = "#id")
    public void deleteLoginDetails(int id) {
        if (!loginDetailsRepository.existsById(id)) {
            throw new LoginDetailsNotFoundException("LoginDetails not found for this id :: " + id);
        }
        loginDetailsRepository.deleteById(id);
    }

    @CacheEvict(value = "loginDetails", key = "#loginDetails.id")
    public LoginDetails saveLoginDetails(LoginDetails loginDetails) {
        return loginDetailsRepository.save(loginDetails);
    }
    
    @Cacheable(value = "loginDetails")
    public List<LoginDetails> getAllLoginDetails() {
        return loginDetailsRepository.findAll();
    }
    
    @CacheEvict(value = "loginDetails", key = "#username")
    public Optional<LoginDetails> getLoginDetailsByUsername(String username) {
        return loginDetailsRepository.findByUsername(username);
    }
}
