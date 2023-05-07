package com.shyftlabs.srm.exceptions.handlers;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shyftlabs.srm.enums.ErrorCode;
import com.shyftlabs.srm.exceptions.CourseNotExistsException;
import com.shyftlabs.srm.exceptions.DuplicateCourseException;
import com.shyftlabs.srm.exceptions.DuplicateResultException;
import com.shyftlabs.srm.exceptions.DuplicateStudentException;
import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.exceptions.StudentNotExistsException;
import com.shyftlabs.srm.responses.ErrorResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		ErrorCode errorCode = ErrorCode.REQUEST_VALIDATION_FAILURE;

		log.error("Request validation error : {}", exception.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(errorCode.getErrorCode(), errorCode.getErrorMessage());

		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler({ CourseNotExistsException.class, StudentNotExistsException.class })
	public ResponseEntity<ErrorResponse> handleNotExistsExceptions(ServiceBaseException exception) {
		ErrorCode errorCode = exception.getErrorCode();

		log.error("Exception : {}", errorCode.getErrorMessage());
		ErrorResponse errorResponse = new ErrorResponse(errorCode.getErrorCode(), errorCode.getErrorMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler({ DuplicateCourseException.class, DuplicateStudentException.class,
			DuplicateResultException.class })
	public ResponseEntity<ErrorResponse> handleDuplicateEntityExceptions(ServiceBaseException exception) {
		ErrorCode errorCode = exception.getErrorCode();

		log.error("Exception : {}", errorCode.getErrorMessage());
		ErrorResponse errorResponse = new ErrorResponse(errorCode.getErrorCode(), errorCode.getErrorMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(OptimisticEntityLockException.class)
	public ResponseEntity<ErrorResponse> handleOptimisticLockFailure(OptimisticEntityLockException exception) {
		ErrorCode errorCode = ErrorCode.OPTIMISTIC_FAILURE;

		log.error("Optimistic lock failure ", exception);
		ErrorResponse errorResponse = new ErrorResponse(errorCode.getErrorCode(), errorCode.getErrorMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUncaughtExceptions(Exception exception) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

		log.error("Unknown error occurred ", exception);
		ErrorResponse errorResponse = new ErrorResponse(errorCode.getErrorCode(), errorCode.getErrorMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

	}
}
