package com.shyftlabs.srm.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class ErrorResponse {
	
	private final String errorCode;
	private final String errorMessage;
	private List<ValidationError> errors;

	@Data
	@AllArgsConstructor
	private static class ValidationError {
		private String field;
		private String message;
	}

	public void addValidationError(String field, String message) {
		if (Objects.isNull(errors)) {
			errors = new ArrayList<>();
		}
		errors.add(new ValidationError(field, message));
	}
}