package org.example.web.validation;

import org.example.web.dto.BookToRemove;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookToRemoveValidator implements Validator {
  @Override
  public boolean supports(Class<?> testClass) {
    return BookToRemove.class.isAssignableFrom(testClass);
  }

  @Override
  public void validate(Object testObject, Errors errors) {
    if (testObject == null) {
      errors.reject("empty", "Remove request is empty");
      return;
    }
    BookToRemove book = (BookToRemove) testObject;
    if (book.isObjectEmpty()) {
      errors.reject("empty", "Remove request is empty");
      return;
    }
    if (book.getSize() != null && (book.getSize() <= 0 || book.getSize() > 9999)) {
      errors.rejectValue("size", "incorrecValue", "Size must be between 0 and 9999");
      return;
    }
  }
}
