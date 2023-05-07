package com.shyftlabs.srm.exceptions.handlers;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shyftlabs.srm.exceptions.CourseNotExistsException;
import com.shyftlabs.srm.exceptions.DuplicateCourseException;
import com.shyftlabs.srm.exceptions.DuplicateResultException;
import com.shyftlabs.srm.exceptions.DuplicateStudentException;
import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.exceptions.StudentNotExistsException;
import com.shyftlabs.srm.responses.ErrorResponse;
import com.shyftlabs.srm.util.ConstantsUtil.ErrorCodes;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		log.error("Request validation error : {}", exception.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.REQUEST_VALIDATION_FAILURE,
				"Validation error. Details in 'error' field.");

		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler({ CourseNotExistsException.class, StudentNotExistsException.class })
	public ResponseEntity<ErrorResponse> handleNotExistsExceptions(ServiceBaseException exception) {
		log.error("Exception : {}", exception.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler({ DuplicateCourseException.class, DuplicateStudentException.class,
			DuplicateResultException.class })
	public ResponseEntity<ErrorResponse> handleDuplicateEntityExceptions(ServiceBaseException exception) {
		log.error("Exception : {}", exception.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(OptimisticEntityLockException.class)
	public ResponseEntity<ErrorResponse> handleOptimisticLockFailure(OptimisticEntityLockException exception) {
		log.error("Optimistic lock failure ", exception);
		ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.OPTIMISTIC_FAILURE,
				"Some error occured. Please try again.");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUncaughtExceptions(Exception exception) {
		log.error("Unknown error occurred ", exception);
		ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR,
				"Some error occured. Please try again.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

	}
}
