package com.shyftlabs.srm.exceptions;

public class DuplicateResultException extends ServiceBaseException {

	private static final long serialVersionUID = 3739824285014209088L;
	
	public DuplicateResultException(String errorCode, String message) {
		super(errorCode, message);
	}
	
}
