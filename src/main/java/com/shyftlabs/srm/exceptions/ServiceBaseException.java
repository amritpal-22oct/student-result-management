package com.shyftlabs.srm.exceptions;

import com.shyftlabs.srm.enums.ErrorCode;

import lombok.Getter;

@Getter
public class ServiceBaseException extends RuntimeException {

	private static final long serialVersionUID = 5431318683600211009L;

	protected ErrorCode errorCode;

	public ServiceBaseException(ErrorCode errorCode) {
		super(errorCode.getErrorMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
