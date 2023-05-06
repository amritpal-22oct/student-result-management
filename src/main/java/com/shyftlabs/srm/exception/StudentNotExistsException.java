package com.shyftlabs.srm.exception;

public class StudentNotExistsException extends ServiceBaseException {

	private static final long serialVersionUID = -6222599436613118739L;

	public StudentNotExistsException(String errorCode, String message) {
		super(errorCode,message);
	}
}
