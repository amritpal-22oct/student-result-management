package com.shyftlabs.srm.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyftlabs.srm.entities.Course;
import com.shyftlabs.srm.entities.Result;
import com.shyftlabs.srm.entities.Student;
import com.shyftlabs.srm.exceptions.CourseNotExistsException;
import com.shyftlabs.srm.exceptions.DuplicateResultException;
import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.exceptions.StudentNotExistsException;
import com.shyftlabs.srm.models.CourseDTO;
import com.shyftlabs.srm.models.ResultDTO;
import com.shyftlabs.srm.models.StudentDTO;
import com.shyftlabs.srm.repositories.CourseRepository;
import com.shyftlabs.srm.repositories.ResultRepository;
import com.shyftlabs.srm.repositories.StudentRepository;
import com.shyftlabs.srm.request.AddResultRequest;
import com.shyftlabs.srm.services.BaseService;
import com.shyftlabs.srm.services.IResultService;
import com.shyftlabs.srm.util.ConstantsUtil.ErrorCodes;
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
		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new CourseNotExistsException(ErrorCodes.COURSE_NOT_EXISTS_WITH_ID,
						String.format("Course with id : %d not present", request.getCourseId())));

		Student student = studentRepository.findById(request.getStudentId())
				.orElseThrow(() -> new StudentNotExistsException(ErrorCodes.STUDENT_NOT_EXISTS_WITH_ID,
						String.format("Student with id : %d not present", request.getStudentId())));

		if (resultRepository.existsByCourseIdAndStudentId(request.getCourseId(), request.getStudentId())) {
			throw new DuplicateResultException(ErrorCodes.RESULT_DUPLICATE_STUDENT_AND_COURSE,
					String.format("Result for combination of student : %d & course : %d already exists",
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
		resultDTO.setStudent(MapperUtils.mapObject(result.getStudent(), StudentDTO.class));
		resultDTO.setCourse(MapperUtils.mapObject(result.getCourse(), CourseDTO.class));
		return resultDTO;
	}
}
