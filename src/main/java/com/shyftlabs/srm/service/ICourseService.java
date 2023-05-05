package com.shyftlabs.srm.service;

import java.util.List;

import com.shyftlabs.srm.exception.ServiceCheckedException;
import com.shyftlabs.srm.model.CourseDTO;
import com.shyftlabs.srm.request.AddCourseRequest;

public interface ICourseService {

	CourseDTO addCourse(AddCourseRequest request) throws ServiceCheckedException;

	List<CourseDTO> getAllCourses();

	void deleteCourse(Long courseId) throws ServiceCheckedException;
}
