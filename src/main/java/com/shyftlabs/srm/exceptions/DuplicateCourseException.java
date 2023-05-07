package com.shyftlabs.srm.exceptions;

import com.shyftlabs.srm.enums.ErrorCode;

public class DuplicateCourseException extends ServiceBaseException {

	private static final long serialVersionUID = -2253064301554434117L;

	public DuplicateCourseException(ErrorCode errorCode) {
		super(errorCode);
	}

}
