package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookToFilter;
import org.example.web.dto.BookToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
  private final ProjectRepository<Book> bookRepo;
  private final Logger logger = Logger.getLogger(BookService.class);

  @Autowired
  public BookService(BookRepository bookRepo) {
    this.bookRepo = bookRepo;
  }

  private void defaultInit() {
    logger.info("DEFAULT INIT (" + this.getClass().getName() + ")");
  }

  private void defaultDestroy() {
    logger.info("DEFAULT DESTROY (" + this.getClass().getName() + ")");
  }

  public List<Book> getAllBooks(BookToFilter bookToFilter) {
    logger.info("get shelf. filter: " + bookToFilter);
    if (bookToFilter == null || bookToFilter.isObjectEmpty()) {
      logger.info("without filters");
      return bookRepo.retrieveAll();
    } else {
      logger.info("with filters");
      return bookRepo.retrieveAll().stream()
          .filter(book ->
                      bookToFilter.isAuthorEmpty() ||
                      book.getAuthor().matches(bookToFilter.getAuthor().replace("*", "(.+)")))
          .filter(book ->
                      bookToFilter.isTitleEmpty() ||
                      book.getTitle().matches(bookToFilter.getTitle().replace("*", "(.+)")))
          .filter(book ->
                      bookToFilter.isSizeEmpty() || book.getSize().equals(bookToFilter.getSize()))
          .collect(Collectors.toList());
    }
  }

  public void saveBook(Book book) {
    if (!book.getAuthor().isEmpty() || !book.getTitle().isEmpty() || (book.getSize() != null && book.getSize() > 0)) {
      bookRepo.store(book);
      logger.info("current repository size: " + getAllBooks(null).size());
    } else {
      logger.info("try to save empty book");
    }
  }

  public boolean removeBook(BookToRemove bookToRemove) {
    logger.info("Remove book: " + bookToRemove);
    if (bookToRemove.getId() != null) {
      if (bookRepo.removeItemById(bookToRemove.getId())) {
        logger.info("removed 1 books");
        return true;
      } else {
        logger.info("removed 0 books");
        return false;
      }
    } else if (bookToRemove.getAuthor() != null && !bookToRemove.getAuthor().isEmpty()) {
      int n = 0;
      for (Book book : bookRepo.retrieveAll()) {
        if (book.getAuthor().matches(bookToRemove.getAuthor().replace("*", "(.+)"))) {
          bookRepo.removeItemById(book.getId());
          n++;
        }
      }
      logger.info("removed " + n + " books");
      return true;
    } else if (bookToRemove.getTitle() != null && !bookToRemove.getTitle().isEmpty()) {
      int n = 0;
      for (Book book : bookRepo.retrieveAll()) {
        if (book.getTitle().matches(bookToRemove.getTitle().replace("*", "(.+)"))) {
          bookRepo.removeItemById(book.getId());
          n++;
        }

      }
      logger.info("removed " + n + " books");
      return true;
    } else if (bookToRemove.getSize() != null && bookToRemove.getSize().intValue() > 0) {
      int n = 0;
      for (Book book : bookRepo.retrieveAll()) {
        if (book.getSize().equals(bookToRemove.getSize())) {
          bookRepo.removeItemById(book.getId());
          n++;
        }

      }
      logger.info("removed " + n + " books");
      return true;
    }
    return false;
  }
}
