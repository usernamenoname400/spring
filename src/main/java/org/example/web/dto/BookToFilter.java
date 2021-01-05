package org.example.web.dto;

public class BookToFilter {
  private Integer id;
  private String author;
  private String title;
  private Integer size;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
    return "BookToFilter{" +
           "id=" + id +
           ", author='" + author + "'" +
           ", title='" + title + "'" +
           ", size=" + size +
           '}';
  }
}
