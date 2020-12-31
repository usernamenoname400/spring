package org.example.web.dto;

public class LoginForm {

  private String userName;
  private String password;

  public LoginForm(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public LoginForm() {
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "LoginForm{" +
           "userName='" + userName + '\'' +
           ", password='" + password + '\'' +
           '}';
  }
}
