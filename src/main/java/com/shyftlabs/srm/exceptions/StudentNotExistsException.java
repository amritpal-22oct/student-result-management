package com.shyftlabs.srm.exceptions;

import com.shyftlabs.srm.enums.ErrorCode;

public class StudentNotExistsException extends ServiceBaseException {

	private static final long serialVersionUID = -6222599436613118739L;

	public StudentNotExistsException(ErrorCode errorCode) {
		super(errorCode);
	}
}
