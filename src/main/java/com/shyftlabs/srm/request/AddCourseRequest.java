package com.shyftlabs.srm.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddCourseRequest {

	@NotBlank(message = "Course cannot be blank")
	@Size(max = 128, message = "Course name cannot be longer than 128 characters")
	private String name;

}
