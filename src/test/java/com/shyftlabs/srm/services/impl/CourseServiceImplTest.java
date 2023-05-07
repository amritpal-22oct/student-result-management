package com.shyftlabs.srm.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shyftlabs.srm.entities.Course;
import com.shyftlabs.srm.exceptions.CourseNotExistsException;
import com.shyftlabs.srm.exceptions.DuplicateCourseException;
import com.shyftlabs.srm.models.CourseDTO;
import com.shyftlabs.srm.repositories.CourseRepository;
import com.shyftlabs.srm.request.AddCourseRequest;

@ExtendWith(SpringExtension.class)
public class CourseServiceImplTest {

	@InjectMocks
	private CourseServiceImpl courseService;

	@Mock
	private CourseRepository courseRepository;

	private Course COURSE_1 = new Course(1L, "Course 1", 0);
	private Course COURSE_2 = new Course(2L, "Course 2", 0);;

	@Test
	public void addCourse_valid() {
		when(courseRepository.save(Mockito.any(Course.class))).thenReturn(COURSE_1);

		CourseDTO savedCourse = courseService.addCourse(new AddCourseRequest());
		assertEquals(savedCourse.getId(), COURSE_1.getId());
	}

	@Test
	public void addCourse_duplicateCourse() {
		when(courseRepository.existsByName(Mockito.any())).thenReturn(Boolean.TRUE);

		Assertions.assertThrows(DuplicateCourseException.class,
				() -> courseService.addCourse(new AddCourseRequest()));
	}

	@Test
	public void getAllCourses_coursesExist() {
		List<Course> expectedCourses = new ArrayList<>();
		expectedCourses.add(COURSE_1);
		expectedCourses.add(COURSE_2);

		when(courseRepository.findAll()).thenReturn(expectedCourses);

		List<CourseDTO> courses = courseService.getAllCourses();
		assertEquals(courses.size(), expectedCourses.size());
	}

	@Test
	public void getAllCourses_noCourseExists() {
		List<Course> expectedCourses = new ArrayList<>();

		when(courseRepository.findAll()).thenReturn(expectedCourses);

		List<CourseDTO> courses = courseService.getAllCourses();
		assertEquals(courses.size(), expectedCourses.size());
	}

	@Test
	public void deleteCourse_courseExists() {
		when(courseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(COURSE_1));

		courseService.deleteCourse(1L);
	}

	@Test
	public void deleteCourse_courseNotExists() {
		when(courseRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		Assertions.assertThrows(CourseNotExistsException.class, () -> courseService.deleteCourse(1L));
	}
}
