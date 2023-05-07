package com.shyftlabs.srm.exceptions;

public class DuplicateCourseException extends ServiceBaseException {

	private static final long serialVersionUID = -2253064301554434117L;

	public DuplicateCourseException(String errorCode, String message) {
		super(errorCode, message);
	}

}
