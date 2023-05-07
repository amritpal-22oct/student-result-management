package com.shyftlabs.srm.enums;

public enum ErrorCode {

	STUDENT_DUPLICATE_EMAIL("SRM_300", "Student with given email id already exists"),
	COURSE_DUPLICATE_NAME("SRM_301", "Course with given name already exists"),
	RESULT_DUPLICATE_STUDENT_AND_COURSE("SRM_302",
			"Result for given combination of student & course id already exists"),
	STUDENT_NOT_EXISTS_WITH_ID("SRM_401", "Student with given id is not present"),
	COURSE_NOT_EXISTS_WITH_ID("SRM_402", "Course with given id is not present"),
	INTERNAL_SERVER_ERROR("SRM_500", "Some error occured. Please try again"),
	OPTIMISTIC_FAILURE("SRM_501", "Some error occured. Please try again"),
	REQUEST_VALIDATION_FAILURE("SRM_400", "Validation error. Details in 'error' field");

	private String errorCode;
	private String errorMessage;

	ErrorCode(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
