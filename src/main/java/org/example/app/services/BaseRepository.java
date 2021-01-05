package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.BaseItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseRepository<T extends BaseItem> implements ProjectRepository<T> {
  protected ArrayList<T> repo = new ArrayList<>();
  protected final Logger logger = Logger.getLogger(this.getClass());

  public List<T> retrieveAll() {
    return new ArrayList<>(repo);
  }

  public boolean removeItemById(Integer itemIdToRemove) {
    T item = findItemById(itemIdToRemove);
    if (item != null) {
      repo.remove(item);
      return true;
    }
    return false;
  }

  public T findItemById(Integer itemIdToFind) {
    for (T item: retrieveAll()) {
      if (item.getId().equals(itemIdToFind)) {
        return item;
      }
    }
    return null;
  }
}
