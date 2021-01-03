package org.example.web.controllers;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.UploadFile;
import org.example.web.validation.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BooksShelfController {
  private final Logger logger = Logger.getLogger(BooksShelfController.class);
  private final BookService bookService;
  private final FileValidator fileValidator;

  @Autowired
  public BooksShelfController(BookService bookService, FileValidator fileValidator) {
    this.bookService = bookService;
    this.fileValidator = fileValidator;
  }

  @GetMapping("/shelf")
  public String books(
      Model model,
      @RequestParam(value="filterByAuthor", defaultValue = "") String filterByAuthor,
      @RequestParam(value="filterByTitle", defaultValue = "") String filterByTitle,
      @RequestParam(value="filterBySize", defaultValue = "") Integer filterBySize
  ) {
    model.addAttribute("book", new Book());
    model.addAttribute("uploadFile", new UploadFile());
    model.addAttribute("bookIdToRemove", new BookIdToRemove());
    model.addAttribute("filterByAuthor", filterByAuthor);
    model.addAttribute("filterByTitle", filterByTitle);
    model.addAttribute("filterBySize", filterBySize);
    model.addAttribute("bookList", bookService.getAllBooks(filterByAuthor, filterByTitle, filterBySize));
    return "books_shelf";
  }

  @PostMapping("/save")
  public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("book", book);
      model.addAttribute("uploadFile", new UploadFile());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookList", bookService.getAllBooks("", "", null));
      return "books_shelf";
    }
    bookService.saveBook(book);
    return "redirect:/books/shelf";
  }

  @PostMapping("/remove")
  public String removeBook(
      @Valid BookIdToRemove bookIdToRemove,
      BindingResult bindingResult,
      Model model
      /*@RequestParam(value="bookIdToRemove") String bookIdToRemove,
      @RequestParam(value="bookAuthorToRemove") String authorIdToRemove,
      @RequestParam(value="bookTitleToRemove") String titleIdToRemove,
      @RequestParam(value="bookSizeToRemove") Integer sizeIdToRemove*/
  ) {
    //bookService.removeBook(bookIdToRemove, authorIdToRemove, titleIdToRemove, sizeIdToRemove);
    logger.info("Remove book");
    if (bindingResult.hasErrors()) {
      logger.info("Incorrect bookIdToRemove " + bookIdToRemove.getId());
      model.addAttribute("book", new Book());
      model.addAttribute("uploadFile", new UploadFile());
      model.addAttribute("bookIdToRemove", bookIdToRemove);
      model.addAttribute("bookList", bookService.getAllBooks("", "", null));
      return "books_shelf";
    }
    bookService.removeBook(bookIdToRemove.getId(), "", "", null);
    return "redirect:/books/shelf";
  }

  @PostMapping("/uploadFile")
  public String uploadFile(
      //@RequestPart MultipartFile file,
      @RequestBody MultipartFile file,
      BindingResult bindingResult,
      Model model
  ) throws Exception {
    /*logger.info("Enter upload");
    logger.info("file " + file);
    fileValidator.validate(file, bindingResult);

    if(bindingResult.hasErrors()) {
      logger.info("File " + file + " is invalid");
      model.addAttribute("book", new Book());
      model.addAttribute("uploadFile", new UploadFile());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookList", bookService.getAllBooks("", "", null));
      return "books_shelf";
    }*/

    String name = file.getOriginalFilename();
    if (name != null) {
      name = name.replace(File.separator, "");
    }
    byte[] bytes = file.getBytes();

    String rootPath = System.getProperty("catalina.home");
    File dir = new File(rootPath + File.separator + "external_uploads");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
    stream.write(bytes);
    stream.close();

    logger.info("File " + serverFile.getAbsolutePath() + " uploaded successfully");
    return "redirect:/books/shelf";
  }

  @RequestMapping("/file/{name:.+}")
  public void getImage(@PathVariable("name") String name, HttpServletResponse response) throws Exception {
    String fileName = name;
    if (fileName != null) {
      fileName = fileName.replace(File.separator, "");
    }
    String rootPath = System.getProperty("catalina.home");
    File dir = new File(rootPath + File.separator + "external_uploads");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

    if (serverFile.exists()) {
      FileInputStream fsIo = new FileInputStream(serverFile);
      response.addHeader("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_VALUE);
      response.addHeader("Content-Length", String.valueOf(serverFile.length()));
      response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
      IOUtils.copy(fsIo, response.getOutputStream());
      response.getOutputStream().flush();
    }
    throw new Exception("File " + fileName + " not found");
  }
}
