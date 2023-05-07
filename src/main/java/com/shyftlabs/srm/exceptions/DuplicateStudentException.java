package com.shyftlabs.srm.exceptions;

import com.shyftlabs.srm.enums.ErrorCode;

public class DuplicateStudentException extends ServiceBaseException {

	private static final long serialVersionUID = 721012249110536966L;

	public DuplicateStudentException(ErrorCode errorCode) {
		super(errorCode);
	}

}
