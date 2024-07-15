package com.cpa.accrt.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpa.accrt.entity.LoginDetails;
import com.cpa.accrt.exception.LoginDetailsNotFoundException;
import com.cpa.accrt.service.LoginDetailsService;

@RestController
@RequestMapping("/api/logindetails")
@CrossOrigin
public class LoginDetailsController {

    @Autowired
    private LoginDetailsService loginDetailsService;
    
    @GetMapping
    public List<LoginDetails> getAllLoginDetails() {
        return loginDetailsService.getAllLoginDetails();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LoginDetails> getLoginDetailsById(@PathVariable int id) {
        Optional<LoginDetails> loginDetails = loginDetailsService.getLoginDetailsById(id);
        if (loginDetails.isPresent()) {
            return ResponseEntity.ok(loginDetails.get());
        } else {
            throw new LoginDetailsNotFoundException("LoginDetails not found for this id :: " + id);
        }
    }
    
    @PostMapping
    public LoginDetails createLoginDetails(@RequestBody LoginDetails loginDetails) {
        System.out.println(loginDetails);
    	return loginDetailsService.saveLoginDetails(loginDetails);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LoginDetails> updateLoginDetails(@PathVariable int id, @RequestBody LoginDetails loginDetailsDetails) {
        Optional<LoginDetails> loginDetails = loginDetailsService.getLoginDetailsById(id);
        if (loginDetails.isPresent()) {
            LoginDetails updatedLoginDetails = loginDetails.get();
            updatedLoginDetails.setUsername(loginDetailsDetails.getUsername());
            updatedLoginDetails.setPassword(loginDetailsDetails.getPassword());
            updatedLoginDetails.setRole(loginDetailsDetails.getRole());
            updatedLoginDetails.setActive(loginDetailsDetails.isActive());
            updatedLoginDetails.setLoggedIn(loginDetailsDetails.isLoggedIn());
            return ResponseEntity.ok(loginDetailsService.saveLoginDetails(updatedLoginDetails));
        } else {
            throw new LoginDetailsNotFoundException("LoginDetails not found for this id :: " + id);
        }
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<LoginDetails> getLoginDetailsByUsername(@PathVariable String username) {
        Optional<LoginDetails> loginDetails = loginDetailsService.getLoginDetailsByUsername(username);
        if (loginDetails.isPresent()) {
            return ResponseEntity.ok(loginDetails.get());
        } else {
            throw new LoginDetailsNotFoundException("LoginDetails not found for this username :: " + username);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoginDetails(@PathVariable int id) {
        if (loginDetailsService.getLoginDetailsById(id).isPresent()) {
            loginDetailsService.deleteLoginDetails(id);
            return ResponseEntity.ok().build();
        } else {
            throw new LoginDetailsNotFoundException("LoginDetails not found for this id :: " + id);
        }
    }
}