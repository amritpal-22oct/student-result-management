package com.shyftlabs.srm.exceptions;

import com.shyftlabs.srm.enums.ErrorCode;

public class DuplicateResultException extends ServiceBaseException {

	private static final long serialVersionUID = 3739824285014209088L;
	
	public DuplicateResultException(ErrorCode errorCode) {
		super(errorCode);
	}
	
}
