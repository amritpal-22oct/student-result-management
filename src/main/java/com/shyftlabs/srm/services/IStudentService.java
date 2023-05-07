package com.shyftlabs.srm.services;

import java.util.List;

import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.models.StudentDTO;
import com.shyftlabs.srm.request.AddStudentRequest;

public interface IStudentService {

	StudentDTO addStudent(AddStudentRequest request) throws ServiceBaseException;
	
	List<StudentDTO> getAllStudents();
	
	void deleteStudent(Long studentId) throws ServiceBaseException;
}
