package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ApplicationContextAware {
  private ProjectRepository<User> userRepo;
  private final Logger logger = Logger.getLogger(BookService.class);
  private ApplicationContext appCtx;

  public List<User> getAllUsers() {
    return userRepo.retrieveAll();
  }

  public boolean saveUser(User user) {
    if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty()) {
      logger.info("current repository size: " + getAllUsers().size());
      return userRepo.store(user);
    } else {
      logger.info("login or password is empty");
      return false;
    }
  }

  public boolean matchLoginPassword(String userLogin, String userPassword) {
    for (User user: userRepo.retrieveAll()) {
      if (user.getLogin().equals(userLogin)) {
        return user.getPassword().equals(userPassword);
      }
    }
    return false;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    appCtx = applicationContext;
    userRepo = appCtx.getBean(UserRepository.class);
  }

  public boolean removeUserById(String userIdToRemove) {
    return userRepo.removeItemById(userIdToRemove);
  }
}
