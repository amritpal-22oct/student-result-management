package com.shyftlabs.srm.service;

import java.util.List;

import com.shyftlabs.srm.exception.ServiceCheckedException;
import com.shyftlabs.srm.model.StudentDTO;
import com.shyftlabs.srm.request.AddStudentRequest;

public interface IStudentService {

	StudentDTO addStudent(AddStudentRequest request) throws ServiceCheckedException;
	
	List<StudentDTO> getAllStudents();
	
	void deleteStudent(Long studentId) throws ServiceCheckedException;
}
