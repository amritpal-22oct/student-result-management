package com.shyftlabs.srm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyftlabs.srm.entities.Student;
import com.shyftlabs.srm.exceptions.DuplicateStudentException;
import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.exceptions.StudentNotExistsException;
import com.shyftlabs.srm.models.StudentDTO;
import com.shyftlabs.srm.repositories.StudentRepository;
import com.shyftlabs.srm.request.AddStudentRequest;
import com.shyftlabs.srm.services.BaseService;
import com.shyftlabs.srm.services.IStudentService;
import com.shyftlabs.srm.util.ConstantsUtil.ErrorCodes;
import com.shyftlabs.srm.util.MapperUtils;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StudentServiceImpl extends BaseService implements IStudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public StudentDTO addStudent(AddStudentRequest request) throws ServiceBaseException {
		if (studentRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateStudentException(ErrorCodes.STUDENT_DUPLICATE_EMAIL,
					String.format("Student with email : %s already exists", request.getEmail()));
		}

		Student student = MapperUtils.mapObject(request, Student.class);
		log.info("Adding student : {}", student);
		student = studentRepository.save(student);
		log.info("Student added with id : {}", student.getId());
		return MapperUtils.mapObject(student, StudentDTO.class);
	}

	@Override
	public List<StudentDTO> getAllStudents() {
		List<Student> students = studentRepository.findAll();
		log.info("Returning {} students", students.size());
		return MapperUtils.mapList(students, StudentDTO.class);
	}

	@Override
	public void deleteStudent(Long studentId) throws ServiceBaseException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentNotExistsException(ErrorCodes.STUDENT_NOT_EXISTS_WITH_ID,
						String.format("Student with id : %d not present", studentId)));

		log.info("Going to delete student : {}", studentId);
		studentRepository.delete(student);
		log.info("Deleted student : {}", studentId);
	}
}
