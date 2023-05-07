package com.shyftlabs.srm.validators;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, LocalDate> {

	int minAge;
	
	@Override
	public void initialize(ValidDateOfBirth constraintAnnotation) {
		this.minAge = constraintAnnotation.minAge();
	}
	
	@Override
	public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {

		return LocalDate.now().getYear() - valueToValidate.getYear() >= minAge;
	}
}
