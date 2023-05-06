package com.shyftlabs.srm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyftlabs.srm.entities.Course;
import com.shyftlabs.srm.exception.CourseNotExistsException;
import com.shyftlabs.srm.exception.ServiceCheckedException;
import com.shyftlabs.srm.model.CourseDTO;
import com.shyftlabs.srm.repository.CourseRepository;
import com.shyftlabs.srm.request.AddCourseRequest;
import com.shyftlabs.srm.service.BaseService;
import com.shyftlabs.srm.service.ICourseService;
import com.shyftlabs.srm.util.MapperUtils;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CourseServiceImpl extends BaseService implements ICourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public CourseDTO addCourse(AddCourseRequest request) throws ServiceCheckedException {
		Course course = MapperUtils.mapObject(request, Course.class);
		log.info("Adding course : {}", course);
		course = courseRepository.save(course);
		log.info("Course added with id : {}", course.getId());
		return MapperUtils.mapObject(course, CourseDTO.class);
	}

	@Override
	public List<CourseDTO> getAllCourses() {
		List<Course> courses = courseRepository.findAll();
		log.info("Returning {} courses", courses.size());
		return MapperUtils.mapList(courses, CourseDTO.class);
	}

	@Override
	public void deleteCourse(Long courseId) throws ServiceCheckedException {
		Course course = courseRepository.findById(courseId).orElseThrow(
				() -> new CourseNotExistsException(String.format("Course with id : {} not present", courseId)));

		log.info("Going to delete course : {}", courseId);
		courseRepository.delete(course);
		log.info("Deleted course : {}", courseId);
	}

}
