package org.example.web.dto;

public class BaseItem {
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "BaseItem{" +
           "id=" + id +
           '}';
  }
}
