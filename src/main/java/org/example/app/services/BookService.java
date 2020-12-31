package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
  private final ProjectRepository<Book> bookRepo;
  private Logger logger = Logger.getLogger(BookService.class);

  @Autowired
  public BookService(BookRepository bookRepo) {
    this.bookRepo = bookRepo;
  }

  public List<Book> getAllBooks(String filterByAuthor, String filterByTitle, Integer filterBySize) {
    logger.info("get shelf. filters: author=" + filterByAuthor + ", title=" + filterByTitle + ", size=" + filterBySize);
    if (
        (filterByAuthor == null || filterByAuthor.equals("")) &&
        (filterByTitle == null || filterByTitle.equals("")) &&
        (filterBySize == null || filterBySize.intValue() <= 0)
        ) {
      logger.info("without filters");
      return bookRepo.retrieveAll();
    } else {
      logger.info("with filters");
      Stream<Book > books = bookRepo.retrieveAll().stream().filter(new Predicate<Book>() {
        private boolean isMatch(String filter, String param) {
          return filter == null || filter.equals("") ||
                 (param != null && param.matches(filter.replace("*", ".+")));
        }
        private boolean isMatch(Integer filter, Integer param) {
          return filter == null || filter.intValue() < 0 ||
                 (param != null && param.equals(filter));
        }
        @Override
        public boolean test(Book book) {
          return (isMatch(filterByAuthor, book.getAuthor()) && isMatch(filterByTitle, book.getTitle()) &&
              isMatch(filterBySize, book.getSize()));
        }
      });
      return new ArrayList<>(books.collect(Collectors.toList()));
    }
  }

  public void saveBook(Book book) {
    if (!book.getAuthor().isEmpty() || !book.getTitle().isEmpty() || (book.getSize() != null && book.getSize() > 0)) {
      bookRepo.store(book);
      logger.info("current repository size: " + getAllBooks("", "", -1).size());
    } else {
      logger.info("try to save empty book");
    }
  }

  public boolean removeBook(Integer idToRemove, String authorToRemove, String titleToRemove, Integer sizeToRemove) {
    logger.info("Remove book: id=" + idToRemove + ", author='" + authorToRemove + "', title='" + titleToRemove + "', " +
                "size=" + sizeToRemove);
    if (idToRemove != null) {
      if (bookRepo.removeItemById(idToRemove)) {
        logger.info("removed 1 books");
      } else {
        logger.info("removed 0 books");
      }
    } else
    if (authorToRemove != null && !authorToRemove.equals("")) {
      int n = 0;
      for (Book book: bookRepo.retrieveAll()) {
        if (book.getAuthor().matches(authorToRemove.replace("*", "(.+)"))) {
          bookRepo.removeItemById(book.getId());
          n++;
        }
      }
      logger.info("removed " + n + " books");
      return true;
    } else
    if (titleToRemove != null && !titleToRemove.equals("")) {
      int n = 0;
      for (Book book: bookRepo.retrieveAll()) {
        if (book.getTitle().matches(titleToRemove.replace("*", "(.+)"))) {
          bookRepo.removeItemById(book.getId());
          n++;
        }

      }
      logger.info("removed " + n + " books");
      return true;
    } else
    if (sizeToRemove != null) {
      int n = 0;
      for (Book book: bookRepo.retrieveAll()) {
        if (book.getSize().equals(sizeToRemove)) {
          bookRepo.removeItemById(book.getId());
          n++;
        }

      }
      logger.info("removed " + n + " books");
      return true;
    }
    return false;
  }

  public void generateBooks() {
    Book book = new Book();
    book.setAuthor("Pushkin");
    book.setTitle("Skazka o pope");
    book.setSize(101);
    bookRepo.store(book);

    book = new Book();
    book.setAuthor("Pushkin");
    book.setTitle("Skazka o pope");
    book.setSize(121);
    bookRepo.store(book);

    book = new Book();
    book.setAuthor("Pushkin");
    book.setTitle("Capitanskaya dochka");
    book.setSize(230);
    bookRepo.store(book);

    book = new Book();
    book.setAuthor("Pushkin");
    book.setTitle("Metel");
    book.setSize(50);
    bookRepo.store(book);

    book = new Book();
    book.setAuthor("Pushkin");
    book.setTitle("Capitanskaya dochka");
    book.setSize(228);
    bookRepo.store(book);

    book = new Book();
    book.setAuthor("Pushkin");
    book.setTitle("Pikovaya dama");
    book.setSize(230);
    bookRepo.store(book);

    book = new Book();
    book.setAuthor("Chekhov");
    book.setTitle("Perpetuum mobile");
    book.setSize(230);
    bookRepo.store(book);

    book = new Book();
    book.setAuthor("Chekhov");
    book.setTitle("V Bane");
    book.setSize(50);
    bookRepo.store(book);
  }
}
