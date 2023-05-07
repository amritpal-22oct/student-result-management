package com.shyftlabs.srm.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StudentDTO {

	private Long id;
	
	private String firstName;
	
	private String familyName;
	
	private String email;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date dateOfBirth;
}
