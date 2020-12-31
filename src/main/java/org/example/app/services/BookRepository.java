package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;


@Repository
public class BookRepository extends BaseRepository<Book> implements ApplicationContextAware {

  private ApplicationContext context;

  @Override
  public boolean store(Book book) {
    book.setId(context.getBean(IdProvider.class).provideId(book));
    logger.info("store new book : " + book);
    repo.add(book);
    logger.info("book list length : " + repo.size());
    return true;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }

  private void defaultInit() {
    logger.info("DEFAULT INIT (" + this.getClass().getName() + ")");
  }

  private void defaultDestroy() {
    logger.info("DEFAULT DESTROY (" + this.getClass().getName() + ")");
  }
}

