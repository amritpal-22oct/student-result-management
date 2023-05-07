package com.shyftlabs.srm.services;

import java.util.List;

import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.models.CourseDTO;
import com.shyftlabs.srm.request.AddCourseRequest;

public interface ICourseService {

	CourseDTO addCourse(AddCourseRequest request) throws ServiceBaseException;

	List<CourseDTO> getAllCourses();

	void deleteCourse(Long courseId) throws ServiceBaseException;
}
