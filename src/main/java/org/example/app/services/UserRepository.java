package org.example.app.services;

import org.example.web.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository extends BaseRepository<User> {

  public UserRepository() {
    User user = new User();
    // add root user
    user.setId("root".hashCode()); // generate id based on login !should be unique!
    user.setLogin("root");
    user.setPassword("123");
    user.setName("Super user");
    repo.add(user);
    logger.info("Initialize repository bean");
  }

  @Override
  public List<User> retrieveAll() {
    return new ArrayList<>(repo);
  }

  @Override
  public boolean store(User user) {
    user.setId(user.getLogin().hashCode()); // generate id based on login !should be unique!
    User storedUser = findItemById(user.getId());
    if (storedUser == null) {
      repo.add(user);
      logger.info("stored new user : " + user);
      logger.info("user list length : " + repo.size());
      return true;
    } else {
      logger.info("user : " + user + " already exists");
      return false;
    }
  }

}
