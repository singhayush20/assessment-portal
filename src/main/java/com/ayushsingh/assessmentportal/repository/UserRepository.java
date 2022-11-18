package com.ayushsingh.assessmentportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.assessmentportal.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	// public void deleteByUsername(String username);

	public List<User> findByEmail(String email);
}
