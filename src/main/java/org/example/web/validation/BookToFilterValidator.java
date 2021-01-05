package org.example.web.validation;

import org.example.web.dto.BookToFilter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookToFilterValidator implements Validator {
  @Override
  public boolean supports(Class<?> testClass) {
    return BookToFilter.class.isAssignableFrom(testClass);
  }

  @Override
  public void validate(Object testObject, Errors errors) {
    if (testObject == null) {
      return;
    }
    BookToFilter book = (BookToFilter) testObject;
    if (book.isObjectEmpty()) {
      return;
    }
    if (book.getSize() != null && (book.getSize() <= 0 || book.getSize() > 9999)) {
      errors.rejectValue("size", "incorrecValue", "Size must be between 0 and 9999");
      return;
    }
  }
}
