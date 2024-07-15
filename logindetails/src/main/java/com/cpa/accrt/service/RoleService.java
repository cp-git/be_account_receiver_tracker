package com.cpa.accrt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cpa.accrt.entity.Role;
import com.cpa.accrt.exception.RoleNotFoundException;
import com.cpa.accrt.repository.RoleRepository;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    
    @Cacheable(value = "roles", key = "#id")
    public Optional<Role> getRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            throw new RoleNotFoundException("Role not found for this id :: " + id);
        }
        return role;
    }
    
    @CacheEvict(value = "roles", key = "#id")
    public void deleteRole(int id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException("Role not found for this id :: " + id);
        }
        roleRepository.deleteById(id);
    }

    @CacheEvict(value = "roles", key = "#role.id")
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
    
    @Cacheable(value = "roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}