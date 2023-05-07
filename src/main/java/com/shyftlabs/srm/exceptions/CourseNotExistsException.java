package com.shyftlabs.srm.exceptions;

public class CourseNotExistsException extends ServiceBaseException {

	private static final long serialVersionUID = -4872207366545190842L;

	public CourseNotExistsException(String errorCode, String message) {
		super(errorCode, message);
	}

}
