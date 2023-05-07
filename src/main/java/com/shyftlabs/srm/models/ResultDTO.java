package com.shyftlabs.srm.models;

import com.shyftlabs.srm.enums.Score;

import lombok.Data;

@Data
public class ResultDTO {
	
	private Long id;
	
	private Score score;

	private CourseDTO course;
	
	private StudentDTO student;
}
