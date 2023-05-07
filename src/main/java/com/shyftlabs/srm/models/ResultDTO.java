package com.shyftlabs.srm.models;

import com.shyftlabs.srm.enums.Score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
	
	private Long id;
	
	private Score score;

	private CourseDTO course;
	
	private StudentDTO student;
}
