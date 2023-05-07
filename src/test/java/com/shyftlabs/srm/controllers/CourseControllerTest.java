package com.shyftlabs.srm.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyftlabs.srm.enums.ErrorCode;
import com.shyftlabs.srm.models.CourseDTO;
import com.shyftlabs.srm.request.AddCourseRequest;
import com.shyftlabs.srm.services.ICourseService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CourseController.class)
public class CourseControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ICourseService courseService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String COURSES_API_ENDPOINT = "/courses";
	
	private CourseDTO COURSE_1 = new CourseDTO(1L, "Course 1");
	private CourseDTO COURSE_2 = new CourseDTO(1L, "Course 2");

	@Test
	public void addCourse_validRequest() throws Exception {
		AddCourseRequest addCourseRequest = new AddCourseRequest("Course 1");

		Mockito.when(courseService.addCourse(Mockito.any(AddCourseRequest.class))).thenReturn(COURSE_1);

		ResultActions response = mockMvc.perform(post(COURSES_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addCourseRequest)));
		response.andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("Course 1")));
	}

	@Test
	public void addCourse_withoutName() throws Exception {
		AddCourseRequest addCourseRequest = new AddCourseRequest("");

		ResultActions response = mockMvc.perform(post(COURSES_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addCourseRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));
	}

	@Test
	public void getAllCourses() throws Exception {
		List<CourseDTO> courses = new ArrayList<>();
		courses.add(COURSE_1);
		courses.add(COURSE_2);

		Mockito.when(courseService.getAllCourses()).thenReturn(courses);

		ResultActions response = mockMvc.perform(get(COURSES_API_ENDPOINT));
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(courses.size())));
	}

	@Test
	public void deleteCourse() throws Exception {
		ResultActions response = mockMvc.perform(delete(COURSES_API_ENDPOINT + "/" + Mockito.anyLong()));
		response.andExpect(status().isOk());
	}

}
