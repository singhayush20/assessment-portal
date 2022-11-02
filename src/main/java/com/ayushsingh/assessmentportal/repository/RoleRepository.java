package com.ayushsingh.assessmentportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.assessmentportal.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
}