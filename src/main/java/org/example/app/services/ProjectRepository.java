package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
  List<T> retrieveAll();

  boolean store(T item);

  boolean removeItemById(String itemIdToRemove);

  T findItemById(String itemIdToFind);
}

