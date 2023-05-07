package com.shyftlabs.srm.validators;

import java.util.Calendar;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, Date> {

	int minAge;

	@Override
	public void initialize(ValidDateOfBirth constraintAnnotation) {
		this.minAge = constraintAnnotation.minAge();
	}

	@Override
	public boolean isValid(final Date valueToValidate, final ConstraintValidatorContext context) {
		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(valueToValidate);

		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) >= minAge;
	}
}
