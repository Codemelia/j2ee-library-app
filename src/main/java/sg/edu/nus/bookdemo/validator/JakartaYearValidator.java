package sg.edu.nus.bookdemo.validator;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class JakartaYearValidator implements ConstraintValidator<MaxYear, Integer> {

	@Override
	public boolean isValid(Integer value, 
		ConstraintValidatorContext context) {
		if (value == null || value == 0) return false;
		return value <= LocalDate.now().getYear();
	}

}
