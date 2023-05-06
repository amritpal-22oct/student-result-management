package com.shyftlabs.srm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyftlabs.srm.entities.Student;
import com.shyftlabs.srm.exception.DuplicateStudentException;
import com.shyftlabs.srm.exception.ServiceBaseException;
import com.shyftlabs.srm.exception.StudentNotExistsException;
import com.shyftlabs.srm.model.StudentDTO;
import com.shyftlabs.srm.repository.StudentRepository;
import com.shyftlabs.srm.request.AddStudentRequest;
import com.shyftlabs.srm.service.BaseService;
import com.shyftlabs.srm.service.IStudentService;
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
			throw new DuplicateStudentException(
					String.format("Student with email : {} already exists", request.getEmail()));
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
		Student student = studentRepository.findById(studentId).orElseThrow(
				() -> new StudentNotExistsException(String.format("Student with id : {} not present", studentId)));

		log.info("Going to delete student : {}", studentId);
		studentRepository.delete(student);
		log.info("Deleted student : {}", studentId);
	}
}
