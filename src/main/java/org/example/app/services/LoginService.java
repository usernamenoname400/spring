package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ApplicationContextAware {
  private final Logger logger = Logger.getLogger(LoginService.class);
  private UserService users;

  public LoginService() {
    users = new UserService();
  }

  public boolean authenticate(LoginForm loginForm) {
    if (users == null) {
      logger.info("Users is null");
      return false;
    } else {
      logger.info("Try authenticate with user-form: " + loginForm);
      return users.matchLoginPassword(loginForm.getUsername(), loginForm.getPassword());
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    users.setApplicationContext(applicationContext);
  }
}
