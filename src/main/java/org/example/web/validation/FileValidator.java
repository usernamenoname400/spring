package org.example.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

  public boolean supports(Class<?> testClass) {
    return MultipartFile.class.isAssignableFrom(testClass);
  }

  public String validate(Object target) {
    if (target == null) {
      return "Cannot validate null";
    }
    if (!supports(target.getClass())) {
      return "Unsupported class " + target.getClass().getName();
    }
    MultipartFile multipartFile = (MultipartFile) target;

    if (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().equals("")) {
      return "File name is empty";
    }
    if (multipartFile.getSize() == 0) {
      return "File is empty";
    }
    return "";
  }
}
