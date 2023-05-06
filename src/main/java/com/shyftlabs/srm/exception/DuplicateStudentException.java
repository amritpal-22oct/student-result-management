package com.shyftlabs.srm.exception;

public class DuplicateStudentException extends ServiceBaseException {

	private static final long serialVersionUID = 721012249110536966L;

	public DuplicateStudentException(String errorCode, String message) {
		super(errorCode,message);
	}

}
