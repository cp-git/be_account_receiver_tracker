package com.cpa.accrt.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpa.accrt.entity.Role;
import com.cpa.accrt.exception.RoleNotFoundException;
import com.cpa.accrt.service.RoleService;


@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
        Optional<Role> role = roleService.getRoleById(id);
        if (role.isPresent()) {
            return ResponseEntity.ok(role.get());
        } else {
            throw new RoleNotFoundException("Role not found for this id :: " + id);
        }
    }
    
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable int id, @RequestBody Role roleDetails) {
        Optional<Role> role = roleService.getRoleById(id);
        if (role.isPresent()) {
            Role updatedRole = role.get();
            updatedRole.setRoleName(roleDetails.getRoleName());
            return ResponseEntity.ok(roleService.saveRole(updatedRole));
        } else {
            throw new RoleNotFoundException("Role not found for this id :: " + id);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        if (roleService.getRoleById(id).isPresent()) {
            roleService.deleteRole(id);
            return ResponseEntity.ok().build();
        } else {
            throw new RoleNotFoundException("Role not found for this id :: " + id);
        }
    }
}