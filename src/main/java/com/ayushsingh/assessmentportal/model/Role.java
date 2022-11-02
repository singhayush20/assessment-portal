package com.ayushsingh.assessmentportal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="roles")
public class Role {
    @Id
    //explicitly give roleId
    @Column(name="roleid")
    private Long roleId;

    @Column(name="rolename",nullable=false,unique=true)
    private String roleName;

    // @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY/*So that userRoles are not fetched unless we call a function */,
    // mappedBy = "role")
    // private List<UserRole> userRoles=new ArrayList<>();

    // @Id
	// private long roleId;
	// private String roleName;
	
	// @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "role")
	// private List<UserRole> userRoles=new ArrayList<>();
}
