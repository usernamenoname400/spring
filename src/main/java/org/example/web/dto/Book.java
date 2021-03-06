package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Book extends BaseItem {
  @NotNull(message="Author must be not empty")
  @NotEmpty(message="Author must be not empty")
  private String author;
  @NotNull(message="Title must be not empty")
  @NotEmpty(message="Title must be not empty")
  private String title;
  @NotNull(message="Size must be not empty")
  @Digits(integer = 4, fraction = 0, message="Size must be integer and less than 9999")
  private Integer size;

  public Book(String author, String title, Integer size)
  {
    this.author = author;
    this.title = title;
    this.size = size;
  }

  public Book()
  {
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public boolean isAuthorEmpty() {
    return author == null || author.isEmpty();
  }

  public boolean isTitleEmpty() {
    return title == null || title.isEmpty();
  }

  public boolean isSizeEmpty() {
    return size == null || size.intValue() <= 0;
  }

  public boolean isObjectEmpty() {
    return getId() == null && isAuthorEmpty() && isTitleEmpty() && isSizeEmpty();
  }

  @Override
  public String toString() {
    return "Book{" +
           "id=" + getId() +
           ", author='" + author + '\'' +
           ", title='" + title + '\'' +
           ", size=" + size +
           '}';
  }
}
