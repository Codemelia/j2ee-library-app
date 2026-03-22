package sg.edu.nus.bookdemo.validator;

import sg.edu.nus.bookdemo.model.Book;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SpringYearValidator implements Validator {

	// ensure only Book object is being validated
	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz); // returns true if clazz is Book
	}

	// main validation logic
	// target - object being validated
	// errors - container to store validation errors
	@Override
	public void validate(Object target, Errors errors) {
		
		// get book and year
		Book book = (Book) target; // cast generic object to book
		Integer year = book.getYear();
		
		// validate year if not null
		if (year != null) {
			int currYear = LocalDate.now().getYear();
			
			if (year > currYear) {
				errors.rejectValue(
					"year", // field name in Book class
					"year.future", // used for message resolution
					"Year cannot be in the future"); // default err message
			}
		}
	}

}
