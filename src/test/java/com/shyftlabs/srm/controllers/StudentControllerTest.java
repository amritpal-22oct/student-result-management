package com.shyftlabs.srm.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
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
import com.shyftlabs.srm.models.StudentDTO;
import com.shyftlabs.srm.request.AddStudentRequest;
import com.shyftlabs.srm.services.IStudentService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StudentController.class)
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IStudentService studentService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String STUDENTS_API_ENDPOINT = "/students";

	private StudentDTO STUDENT_1 = new StudentDTO(1L, "Test1", "User", "test1@gmail.com",
			Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));
	private StudentDTO STUDENT_2 = new StudentDTO(2L, "Test2", "User", "test2@gmail.com",
			Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));
	private StudentDTO STUDENT_3 = new StudentDTO(3L, "Test3", "User", "test3@gmail.com",
			Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));

	@Test
	public void addStudent_validRequest() throws Exception {
		AddStudentRequest addStudentRequest = new AddStudentRequest("Test1", "User", "test1@gmail.com",
				Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));

		Mockito.when(studentService.addStudent(Mockito.any(AddStudentRequest.class))).thenReturn(STUDENT_1);

		ResultActions response = mockMvc.perform(post(STUDENTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addStudentRequest)));
		response.andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Test1")));
	}

	@Test
	public void addStudent_withoutFirstName() throws Exception {
		AddStudentRequest addStudentRequest = new AddStudentRequest("", "User", "test1@gmail.com",
				Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));

		Mockito.when(studentService.addStudent(Mockito.any(AddStudentRequest.class))).thenReturn(STUDENT_1);

		ResultActions response = mockMvc.perform(post(STUDENTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addStudentRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));
	}

	@Test
	public void addStudent_withoutLastName() throws Exception {
		AddStudentRequest addStudentRequest = new AddStudentRequest("Test1", "", "test1@gmail.com",
				Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));

		ResultActions response = mockMvc.perform(post(STUDENTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addStudentRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));

	}

	@Test
	public void addStudent_invalidEmail() throws Exception {
		AddStudentRequest addStudentRequest = new AddStudentRequest("Test1", "User", "test1.com",
				Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));

		ResultActions response = mockMvc.perform(post(STUDENTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addStudentRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));

	}

	@Test
	public void addStudent_ageLessThanRequired() throws Exception {
		AddStudentRequest addStudentRequest = new AddStudentRequest("Test1", "User", "test1@gmail.com",
				Date.valueOf(LocalDate.of(2020, Month.JANUARY, 1)));

		ResultActions response = mockMvc.perform(post(STUDENTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addStudentRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));

	}

	@Test
	public void getAllStudents() throws Exception {
		List<StudentDTO> students = new ArrayList<>();
		students.add(STUDENT_1);
		students.add(STUDENT_2);
		students.add(STUDENT_3);

		Mockito.when(studentService.getAllStudents()).thenReturn(students);

		ResultActions response = mockMvc.perform(get(STUDENTS_API_ENDPOINT));
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(students.size())));
	}

	@Test
	public void deleteStudent() throws Exception {
		ResultActions response = mockMvc.perform(delete(STUDENTS_API_ENDPOINT + "/" + Mockito.anyLong()));
		response.andExpect(status().isOk());
	}
}
