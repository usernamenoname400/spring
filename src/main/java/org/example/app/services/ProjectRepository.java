package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
  List<T> retrieveAll();

  boolean store(T item);

  boolean removeItemById(Integer itemIdToRemove);

  T findItemById(Integer itemIdToFind);
}

