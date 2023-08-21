package com.ayushsingh.assessmentportal.model;




import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

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


}
