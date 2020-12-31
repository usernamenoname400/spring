package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequestMapping(value = "/books")
public class BooksShelfController {
  private Logger logger = Logger.getLogger(BooksShelfController.class);
  private BookService bookService;

  @Autowired
  public BooksShelfController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/shelf")
  public String books(
      Model model,
      @RequestParam(value="filterByAuthor", defaultValue = "") String filterByAuthor,
      @RequestParam(value="filterByTitle", defaultValue = "") String filterByTitle,
      @RequestParam(value="filterBySize", defaultValue = "") Integer filterBySize
  ) {
    logger.info("got book shelf");
    model.addAttribute("book", new Book());
    model.addAttribute("filterByAuthor", filterByAuthor);
    model.addAttribute("filterByTitle", filterByTitle);
    model.addAttribute("filterBySize", filterBySize);
    model.addAttribute("bookList", bookService.getAllBooks(filterByAuthor, filterByTitle, filterBySize));
    return "books_shelf";
  }

  @PostMapping("/save")
  public String saveBook(Book book) {
    bookService.saveBook(book);
    return "redirect:/books/shelf";
  }

  @PostMapping("/remove")
  public String removeBook(
      @RequestParam(value="bookIdToRemove") Integer bookIdToRemove,
      @RequestParam(value="bookAuthorToRemove") String authorIdToRemove,
      @RequestParam(value="bookTitleToRemove") String titleIdToRemove,
      @RequestParam(value="bookSizeToRemove") Integer sizeIdToRemove
  ) {
    bookService.removeBook(bookIdToRemove, authorIdToRemove, titleIdToRemove, sizeIdToRemove);
    return "redirect:/books/shelf";
  }

  @PostMapping("/generate")
  public String generateBooks() {
    bookService.generateBooks();
    return "redirect:/books/shelf";
  }
}
