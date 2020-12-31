package org.example.web.dto;

public class User extends BaseItem {
  private String login;
  private String password;
  private String name;

  public String getPassword(){
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
           "id=" + getId() +
           ", login='" + login + '\'' +
           ", name='" + name + '\'' +
           '}';
  }
}
