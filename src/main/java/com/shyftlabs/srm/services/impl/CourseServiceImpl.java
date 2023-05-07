package com.shyftlabs.srm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyftlabs.srm.entities.Course;
import com.shyftlabs.srm.exceptions.CourseNotExistsException;
import com.shyftlabs.srm.exceptions.DuplicateCourseException;
import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.models.CourseDTO;
import com.shyftlabs.srm.repositories.CourseRepository;
import com.shyftlabs.srm.request.AddCourseRequest;
import com.shyftlabs.srm.services.BaseService;
import com.shyftlabs.srm.services.ICourseService;
import com.shyftlabs.srm.util.ConstantsUtil.ErrorCodes;
import com.shyftlabs.srm.util.MapperUtils;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CourseServiceImpl extends BaseService implements ICourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public CourseDTO addCourse(AddCourseRequest request) throws ServiceBaseException {
		if (courseRepository.existsByName(request.getName())) {
			throw new DuplicateCourseException(ErrorCodes.COURSE_DUPLICATE_NAME,
					String.format("Course with name : %s already exists", request.getName()));
		}

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
	public void deleteCourse(Long courseId) throws ServiceBaseException {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotExistsException(ErrorCodes.COURSE_NOT_EXISTS_WITH_ID,
						String.format("Course with id : %d not present", courseId)));

		log.info("Going to delete course : {}", courseId);
		courseRepository.delete(course);
		log.info("Deleted course : {}", courseId);
	}

}
