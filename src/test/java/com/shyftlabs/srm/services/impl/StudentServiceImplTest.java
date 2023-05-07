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

import com.shyftlabs.srm.entities.Student;
import com.shyftlabs.srm.exceptions.DuplicateStudentException;
import com.shyftlabs.srm.exceptions.StudentNotExistsException;
import com.shyftlabs.srm.models.StudentDTO;
import com.shyftlabs.srm.repositories.StudentRepository;
import com.shyftlabs.srm.request.AddStudentRequest;

@ExtendWith(SpringExtension.class)
public class StudentServiceImplTest {

	@InjectMocks
	private StudentServiceImpl studentService;

	@Mock
	private StudentRepository studentRepository;

	private Student STUDENT_1 = new Student(1L, "Test1", "User", "test1@gmail.com",
			Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)), 0);
	private Student STUDENT_2 = new Student(2L, "Test2", "User", "test2@gmail.com",
			Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)), 0);

	@Test
	public void addStudent_valid() {
		when(studentRepository.save(Mockito.any(Student.class))).thenReturn(STUDENT_1);

		StudentDTO savedStudent = studentService.addStudent(new AddStudentRequest());
		assertEquals(savedStudent.getId(), STUDENT_1.getId());
	}

	@Test
	public void addStudent_duplicateEmail() {
		when(studentRepository.existsByEmail(Mockito.any())).thenReturn(Boolean.TRUE);
		
		Assertions.assertThrows(DuplicateStudentException.class,
				() -> studentService.addStudent(new AddStudentRequest()));
	}

	@Test
	public void getAllStudents_studentsExist() {
		List<Student> expectedStudents = new ArrayList<>();
		expectedStudents.add(STUDENT_1);
		expectedStudents.add(STUDENT_2);

		when(studentRepository.findAll()).thenReturn(expectedStudents);

		List<StudentDTO> students = studentService.getAllStudents();
		assertEquals(students.size(), expectedStudents.size());
	}

	@Test
	public void getAllStudents_noStudentExists() {
		List<Student> expectedStudents = new ArrayList<>();

		when(studentRepository.findAll()).thenReturn(expectedStudents);

		List<StudentDTO> students = studentService.getAllStudents();
		assertEquals(students.size(), expectedStudents.size());
	}

	@Test
	public void deleteStudent_studentExists() {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(STUDENT_1));

		studentService.deleteStudent(1L);
	}

	@Test
	public void deleteStudent_studentNotExists() {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		Assertions.assertThrows(StudentNotExistsException.class, () -> studentService.deleteStudent(1L));
	}

}
