package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;


@Repository
public class BookRepository extends BaseRepository<Book> {

  @Override
  public boolean store(Book book) {
    book.setId(book.hashCode());
    logger.info("store new book : " + book);
    repo.add(book);
    logger.info("book list length : " + repo.size());
    return true;
  }
}

