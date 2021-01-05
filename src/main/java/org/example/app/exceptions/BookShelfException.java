package org.example.app.exceptions;

public class BookShelfException extends Exception {
  private final String message;

  public BookShelfException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
