package com.shyftlabs.srm.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shyftlabs.srm.enums.Score;
import com.shyftlabs.srm.validators.ValueOfEnum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddResultRequest {

	@NotNull(message = "Student id not specified")
	@Positive(message = "Student id is invalid")
	private Long studentId;

	@NotNull(message = "Course id not specified")
	@Positive(message = "Course id is invalid")
	private Long courseId;

	@ValueOfEnum(enumClass = Score.class)
	private String score;
}
