package com.ayushsingh.assessmentportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ayushsingh.assessmentportal.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.user_name = ?1")
	public User findByUser_name(String username);
	// public void deleteByUsername(String username);

	public List<User> findByEmail(String email);

	
}
