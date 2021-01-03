package org.example.web.validation;

import org.example.web.dto.UploadFile;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator implements Validator {

  public boolean supports(Class<?> testClass) {
    return MultipartFile.class.isAssignableFrom(testClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    UploadFile uploadFile = (UploadFile) target;

    MultipartFile multipartFile = uploadFile.getFile();

    if (multipartFile.getName() == null || multipartFile.getName().equals("") || multipartFile.getSize() == 0) {
        errors.rejectValue("files", "missing.file");
    }
  }

}
