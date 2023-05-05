package com.shyftlabs.srm.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shyftlabs.srm.enums.Score;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddResultRequest {

	@NotNull(message = "Student id not specified")
	@Positive(message = "Student id is invalid")
	private Long studentId;
	
	@NotNull(message = "Course id not specified")
	@Positive(message = "Course id is invalid")
	private Long courseId;
	
	@NotNull(message = "Score not specified")
	private Score score;
}
