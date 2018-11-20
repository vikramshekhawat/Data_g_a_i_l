package com.gail.customAnnotation.validators;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gail.customAnnotation.DateFormat;

public class DateFormatValidator implements ConstraintValidator<DateFormat, Date> {

	public void initialize(DateFormat annotation) {
	}

	public boolean isValid(Date date, ConstraintValidatorContext context) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String stringDate = df.format(date);
			return stringDate.matches("\\d{4}-\\d{2}-\\d{2}");
		} else
			return true;
	}

}