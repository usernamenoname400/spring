package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {
  protected final Logger logger = Logger.getLogger(this.getClass());
  private ApplicationContext context;
  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Book> retrieveAll() {
    List<Book> books = jdbcTemplate.query("select * from books", (ResultSet rs, int rowNum) -> {
      Book book = new Book();
      book.setId(rs.getInt("id"));
      book.setAuthor(rs.getString("author"));
      book.setTitle(rs.getString("title"));
      book.setSize(rs.getInt("size"));
      return book;
    });
    return new ArrayList<>(books);
  }

  @Override
  public boolean removeItemById(Integer itemIdToRemove) {
    logger.info("delete book : " + itemIdToRemove);
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("id", itemIdToRemove);
    int qnt = jdbcTemplate.update("delete from books where id = :id", parameterSource);
    if (qnt > 0) {
      logger.info("Deleted " + qnt + " books");
      return true;
    }
    logger.info("Book with id " + itemIdToRemove + " not found");
    return false;
  }

  @Override
  public boolean store(Book book) {
    logger.info("store new book : " + book);
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("author", book.getAuthor());
    parameterSource.addValue("title", book.getTitle());
    parameterSource.addValue("size", book.getSize());
    jdbcTemplate.update("insert into books(author, title, size) values(:author, :title, :size)", parameterSource);
    return true;
  }

  @Override
  public Book findItemById(Integer itemIdToFind) {
    for (Book item: retrieveAll()) {
      if (item.getId().equals(itemIdToFind)) {
        return item;
      }
    }
    return null;
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

