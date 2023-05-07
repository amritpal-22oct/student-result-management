package com.shyftlabs.srm.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
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
import com.shyftlabs.srm.entities.Result;
import com.shyftlabs.srm.entities.Student;
import com.shyftlabs.srm.enums.Score;
import com.shyftlabs.srm.exceptions.CourseNotExistsException;
import com.shyftlabs.srm.exceptions.DuplicateResultException;
import com.shyftlabs.srm.exceptions.StudentNotExistsException;
import com.shyftlabs.srm.models.ResultDTO;
import com.shyftlabs.srm.repositories.CourseRepository;
import com.shyftlabs.srm.repositories.ResultRepository;
import com.shyftlabs.srm.repositories.StudentRepository;
import com.shyftlabs.srm.request.AddResultRequest;

@ExtendWith(SpringExtension.class)
public class ResultServiceImplTest {

	@InjectMocks
	private ResultServiceImpl resultService;

	@Mock
	private ResultRepository resultRepository;

	@Mock
	private StudentRepository studentRepository;

	@Mock
	private CourseRepository courseRepository;

	private Result RESULT_1 = new Result(1L, Score.A,
			new Student(1L, "Test1", "User", "test1@gmail.com", Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)), 0),
			new Course(1L, "Course 1", 0), 0);
	private Result RESULT_2 = new Result(2L, Score.A,
			new Student(1L, "Test1", "User", "test1@gmail.com", Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)), 0),
			new Course(2L, "Course 1", 0), 0);

	@Test
	public void addResult_valid() {
		when(courseRepository.findById(Mockito.any())).thenReturn(Optional.of(RESULT_1.getCourse()));
		when(studentRepository.findById(Mockito.any())).thenReturn(Optional.of(RESULT_1.getStudent()));
		when(resultRepository.save(Mockito.any(Result.class))).thenReturn(RESULT_1);

		ResultDTO savedResult = resultService.addResult(new AddResultRequest());
		assertEquals(savedResult.getId(), RESULT_1.getId());
	}

	@Test
	public void addResult_courseNotExists() {
		when(courseRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		when(studentRepository.findById(Mockito.any())).thenReturn(Optional.of(RESULT_1.getStudent()));

		Assertions.assertThrows(CourseNotExistsException.class, () -> resultService.addResult(new AddResultRequest()));
	}

	@Test
	public void addResult_studentNotExists() {
		when(courseRepository.findById(Mockito.any())).thenReturn(Optional.of(RESULT_1.getCourse()));
		when(studentRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		when(resultRepository.save(Mockito.any(Result.class))).thenReturn(RESULT_1);

		Assertions.assertThrows(StudentNotExistsException.class, () -> resultService.addResult(new AddResultRequest()));
	}

	@Test
	public void addResult_duplicateResult() {
		when(courseRepository.findById(Mockito.any())).thenReturn(Optional.of(RESULT_1.getCourse()));
		when(studentRepository.findById(Mockito.any())).thenReturn(Optional.of(RESULT_1.getStudent()));

		when(resultRepository.existsByCourseIdAndStudentId(Mockito.any(), Mockito.any())).thenReturn(Boolean.TRUE);

		Assertions.assertThrows(DuplicateResultException.class, () -> resultService.addResult(new AddResultRequest()));
	}

	@Test
	public void getAllResults_resultsExist() {
		List<Result> expectedResults = new ArrayList<>();
		expectedResults.add(RESULT_1);
		expectedResults.add(RESULT_2);

		when(resultRepository.findAll()).thenReturn(expectedResults);

		List<ResultDTO> results = resultService.getAllResults();
		assertEquals(results.size(), expectedResults.size());
	}

	@Test
	public void getAllResults_noResultExists() {
		List<Result> expectedResults = new ArrayList<>();

		when(resultRepository.findAll()).thenReturn(expectedResults);

		List<ResultDTO> results = resultService.getAllResults();
		assertEquals(results.size(), expectedResults.size());
	}
}
