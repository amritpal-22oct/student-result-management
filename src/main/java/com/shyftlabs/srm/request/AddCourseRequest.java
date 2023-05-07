package com.shyftlabs.srm.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseRequest {

	@NotBlank(message = "Course cannot be blank")
	@Size(max = 128, message = "Course name cannot be longer than 128 characters")
	private String name;

}
