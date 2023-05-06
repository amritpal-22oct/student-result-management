package com.shyftlabs.srm.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyftlabs.srm.entities.Course;
import com.shyftlabs.srm.entities.Result;
import com.shyftlabs.srm.entities.Student;
import com.shyftlabs.srm.exception.CourseNotExistsException;
import com.shyftlabs.srm.exception.DuplicateResultException;
import com.shyftlabs.srm.exception.ServiceBaseException;
import com.shyftlabs.srm.exception.StudentNotExistsException;
import com.shyftlabs.srm.model.CourseDTO;
import com.shyftlabs.srm.model.ResultDTO;
import com.shyftlabs.srm.model.StudentDTO;
import com.shyftlabs.srm.repository.CourseRepository;
import com.shyftlabs.srm.repository.ResultRepository;
import com.shyftlabs.srm.repository.StudentRepository;
import com.shyftlabs.srm.request.AddResultRequest;
import com.shyftlabs.srm.service.BaseService;
import com.shyftlabs.srm.service.IResultService;
import com.shyftlabs.srm.util.MapperUtils;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ResultServiceImpl extends BaseService implements IResultService {

	@Autowired
	private ResultRepository resultRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public ResultDTO addResult(AddResultRequest request) throws ServiceBaseException {
		Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new CourseNotExistsException(
				String.format("Course with id : {} not present", request.getCourseId())));

		Student student = studentRepository.findById(request.getStudentId())
				.orElseThrow(() -> new StudentNotExistsException(
						String.format("Student with id : {} not present", request.getStudentId())));

		if (resultRepository.existsByCourseIdAndStudentId(request.getCourseId(), request.getStudentId())) {
			throw new DuplicateResultException(
					String.format("Result for combination student : {} & course : {} already exists",
							request.getStudentId(), request.getCourseId()));
		}

		Result result = MapperUtils.mapObject(request, Result.class);
		result.setStudent(student);
		result.setCourse(course);

		log.info("Adding result : {}", result);
		result = resultRepository.save(result);
		log.info("Result added with id : {}", result.getId());

		return mapToResultDTO(result);
	}

	@Override
	public List<ResultDTO> getAllResults() {
		List<Result> results = resultRepository.findAll();
		log.info("Returning {} results", results.size());
		return results.stream().map(this::mapToResultDTO).collect(Collectors.toList());
	}

	private ResultDTO mapToResultDTO(Result result) {
		ResultDTO resultDTO = MapperUtils.mapObject(result, ResultDTO.class);
		resultDTO.setStudentDTO(MapperUtils.mapObject(result.getStudent(), StudentDTO.class));
		resultDTO.setCourseDTO(MapperUtils.mapObject(result.getCourse(), CourseDTO.class));
		return resultDTO;
	}
}
