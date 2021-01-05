package org.example.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

  private MultipartFile file;
  private String errorMessage;
  private String infoMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getInfoMessage() {
    return infoMessage;
  }

  public void setInfoMessage(String infoMessage) {
    this.infoMessage = infoMessage;
  }

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "UploadFile{" +
           "file name='" + (file == null ? "" : file.getName()) + "'" +
           ", file size=" + (file == null ? "0" : file.getSize()) +
           ", errorMessage='" + errorMessage + "'" +
           ", infoMessage='" + infoMessage + "'" +
           '}';
  }
}
