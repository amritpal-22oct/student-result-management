package com.shyftlabs.srm.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddStudentRequest {

	@NotBlank(message = "First name cannot be blank")
	@Size(max = 40, message = "First name cannot be longer than 40 characters")
	private String firstName;

	@NotBlank(message = "Family name cannot be blank")
	@Size(max = 40, message = "Family name cannot be longer than 40 characters")
	private String familyName;

	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Email is not valid")
	@Size(max = 128, message = "Email cannot be longer than 128 characters")
	@NotBlank(message = "Email cannot be blank")
	private String email;

	@NotNull(message = "Date of birth cannot be blank")
	private Date dateOfBirth;
}
