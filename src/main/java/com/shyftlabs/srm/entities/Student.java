package com.shyftlabs.srm.entities;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "family_name")
	private String familyName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Version
	@Column(name = "version")
	private Integer version;
}
