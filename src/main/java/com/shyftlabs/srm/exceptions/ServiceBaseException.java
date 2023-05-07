package com.shyftlabs.srm.exceptions;

import lombok.Getter;

@Getter
public class ServiceBaseException extends RuntimeException {

	private static final long serialVersionUID = 5431318683600211009L;
	
	private String errorCode;

	public ServiceBaseException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
