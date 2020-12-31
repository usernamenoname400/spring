package org.example.web.dto;

public class BaseItem {
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "BaseItem{" +
           "id=" + id +
           '}';
  }
}
