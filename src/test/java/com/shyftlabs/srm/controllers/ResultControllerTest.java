package com.shyftlabs.srm.controllers;

import static org.hamcrest.CoreMatchers.is;
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
import com.shyftlabs.srm.enums.Score;
import com.shyftlabs.srm.models.CourseDTO;
import com.shyftlabs.srm.models.ResultDTO;
import com.shyftlabs.srm.models.StudentDTO;
import com.shyftlabs.srm.request.AddResultRequest;
import com.shyftlabs.srm.services.IResultService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ResultController.class)
public class ResultControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IResultService resultService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String RESULTS_API_ENDPOINT = "/results";
	
	private ResultDTO RESULT_1 = new ResultDTO(1L, Score.A, new CourseDTO(1L, "Course 1"),new StudentDTO(1L, "Test1", "User", "test1@gmail.com",
			Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1))));
	private ResultDTO RESULT_2 = new ResultDTO(2L, Score.B, new CourseDTO(2L, "Course 2"),new StudentDTO(1L, "Test1", "User", "test1@gmail.com",
			Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1))));

	@Test
	public void addResult_validRequest() throws Exception {
		AddResultRequest addResultRequest = new AddResultRequest(1L,1L,Score.A.toString());

		Mockito.when(resultService.addResult(Mockito.any(AddResultRequest.class))).thenReturn(RESULT_1);

		ResultActions response = mockMvc.perform(post(RESULTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addResultRequest)));
		response.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	public void addResult_withoutStudentId() throws Exception {
		AddResultRequest addResultRequest = new AddResultRequest(null,1L,Score.A.toString());

		ResultActions response = mockMvc.perform(post(RESULTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addResultRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));
	}
	
	@Test
	public void addResult_withoutCourseId() throws Exception {
		AddResultRequest addResultRequest = new AddResultRequest(1L,null,Score.A.toString());

		ResultActions response = mockMvc.perform(post(RESULTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addResultRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));
	}
	
	@Test
	public void addResult_withoutScore() throws Exception {
		AddResultRequest addResultRequest = new AddResultRequest(1L,1L,null);

		ResultActions response = mockMvc.perform(post(RESULTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addResultRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));
	}
	
	@Test
	public void addResult_invalidScore() throws Exception {
		AddResultRequest addResultRequest = new AddResultRequest(1L,1L,"X");

		ResultActions response = mockMvc.perform(post(RESULTS_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addResultRequest)));
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorCode())))
				.andExpect(jsonPath("$.errorMessage", is(ErrorCode.REQUEST_VALIDATION_FAILURE.getErrorMessage())));
	}

	@Test
	public void getAllResults() throws Exception {
		List<ResultDTO> results = new ArrayList<>();
		results.add(RESULT_1);
		results.add(RESULT_2);

		Mockito.when(resultService.getAllResults()).thenReturn(results);

		ResultActions response = mockMvc.perform(get(RESULTS_API_ENDPOINT));
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(results.size())));
	}

}