package com.ayushsingh.assessmentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.assessmentportal.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    
    public Category findByTitle(String title);
}
