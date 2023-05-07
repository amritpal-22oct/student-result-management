package com.shyftlabs.srm.exceptions;

import com.shyftlabs.srm.enums.ErrorCode;

public class CourseNotExistsException extends ServiceBaseException {

	private static final long serialVersionUID = -4872207366545190842L;

	public CourseNotExistsException(ErrorCode errorCode) {
		super(errorCode);
	}

}
