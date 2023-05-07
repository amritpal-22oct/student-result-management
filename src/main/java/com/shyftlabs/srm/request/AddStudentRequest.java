package com.shyftlabs.srm.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shyftlabs.srm.validators.ValidDateOfBirth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentRequest {

	@NotBlank(message = "First name cannot be blank")
	@Size(max = 40, message = "First name cannot be longer than 40 characters")
	private String firstName;

	@NotBlank(message = "Family name cannot be blank")
	@Size(max = 40, message = "Family name cannot be longer than 40 characters")
	private String familyName;

	@Size(max = 128, message = "Email cannot be longer than 128 characters")
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Email is not valid")
	private String email;

	@Past(message = "Date of birth is not valid")
	@ValidDateOfBirth(minAge = 10, message = "Student must be 10 years old")
	private Date dateOfBirth;
}
