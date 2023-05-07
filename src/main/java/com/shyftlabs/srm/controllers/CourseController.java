package com.shyftlabs.srm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shyftlabs.srm.models.CourseDTO;
import com.shyftlabs.srm.request.AddCourseRequest;
import com.shyftlabs.srm.services.ICourseService;

import jakarta.validation.Valid;

@RestController
public class CourseController {

	@Autowired
	private ICourseService courseService;

	@PostMapping("/courses")
	public ResponseEntity<CourseDTO> addCourse(@Valid @RequestBody AddCourseRequest request) {
		return ResponseEntity
		        .status(HttpStatus.CREATED)
		        .body(courseService.addCourse(request));
	}

	@GetMapping("/courses")
	ResponseEntity<List<CourseDTO>> getAllCourses() {
		return ResponseEntity
		        .status(HttpStatus.OK)
		        .body(courseService.getAllCourses());
	}

	@DeleteMapping("/courses/{id}")
	ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		courseService.deleteCourse(id);
		return ResponseEntity
		        .status(HttpStatus.OK).build();
	}

}
