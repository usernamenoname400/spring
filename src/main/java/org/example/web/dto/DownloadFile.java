package org.example.web.dto;

public class DownloadFile {
  private String name;

  public DownloadFile(String name) {
    this.name = name;
  }

  public DownloadFile() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "DownloadFile{name='" + name + "'}";
  }
}
