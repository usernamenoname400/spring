package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
  private final Logger logger = Logger.getLogger(this.getClass());
  private final AuthenticationManagerBuilder auth;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) {
    this.auth = auth;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean saveUser(User user) throws Exception {
    if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty()) {
      logger.info(auth);
      logger.info(passwordEncoder);
      auth
          .inMemoryAuthentication()
          .withUser(user.getLogin())
          .password(passwordEncoder.encode(user.getPassword()))
          .roles("USER");
      logger.info("user " + user + " saved");
      return true;
    } else {
      logger.info("login or password is empty");
      return false;
    }
  }
}
