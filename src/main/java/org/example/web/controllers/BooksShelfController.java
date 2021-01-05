package org.example.web.controllers;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.app.services.FileService;
import org.example.web.dto.Book;
import org.example.web.dto.BookToFilter;
import org.example.web.dto.BookToRemove;
import org.example.web.dto.UploadFile;
import org.example.web.validation.BookToFilterValidator;
import org.example.web.validation.BookToRemoveValidator;
import org.example.web.validation.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;

@Controller
@RequestMapping(value = "/books")
@Scope("session")
public class BooksShelfController {
  private final Logger logger = Logger.getLogger(BooksShelfController.class);
  private final BookService bookService;
  private final FileService fileService;
  private final FileValidator fileValidator;
  private final BookToRemoveValidator bookToRemoveValidator;
  private final BookToFilterValidator bookToFilterValidator;

  private BookToFilter bookToFilter = new BookToFilter();

  @Autowired
  public BooksShelfController(
      BookService bookService,
      FileService fileService,
      FileValidator fileValidator,
      BookToRemoveValidator bookToRemoveValidator,
      BookToFilterValidator bookToFilterValidator
  ) {
    this.bookService = bookService;
    this.fileService = fileService;
    this.fileValidator = fileValidator;
    this.bookToRemoveValidator = bookToRemoveValidator;
    this.bookToFilterValidator = bookToFilterValidator;
  }

  private void prepareModel(
      Model model,
      Book book,
      BookToRemove bookToRemove,
      UploadFile uploadFile,
      BookToFilter bookToFilter
  ) {
    model.addAttribute("book", book == null ? new Book() : book);
    model.addAttribute("uploadFile", uploadFile == null ? new UploadFile() : uploadFile);
    model.addAttribute("bookToRemove", bookToRemove == null ? new BookToRemove() : bookToRemove);
    model.addAttribute("fileList", fileService.getFiles());
    logger.info(this.bookToFilter);
    logger.info(bookToFilter);
    model.addAttribute("bookList", bookService.getAllBooks(this.bookToFilter));
    model.addAttribute("bookToFilter", bookToFilter == null ? this.bookToFilter : bookToFilter);
  }

  @GetMapping("/shelf")
  public String books(BookToFilter bookToFilter, BindingResult bindingResult, Model model) {
    bookToFilterValidator.validate(bookToFilter, bindingResult);
    if (bindingResult.hasErrors()) {
      logger.info("filter has errors");
      for (FieldError err :  bindingResult.getFieldErrors()) {logger.info(err.getField() + " : " + err.getDefaultMessage());}
      prepareModel(model, null, null, null, bookToFilter);
      return "books_shelf";
    }

    logger.info("prepare shelf; filter: " + bookToFilter);
    if (bookToFilter != null) {
      this.bookToFilter = bookToFilter;
    }
    prepareModel(model, null, null, null, null);
    return "books_shelf";
  }

  @PostMapping("/save")
  public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      prepareModel(model, book,null, null, null);
      return "books_shelf";
    }
    bookService.saveBook(book);
    return "redirect:/books/shelf";
  }

  @PostMapping("/remove")
  public String removeBook(BookToRemove bookToRemove, BindingResult bindingResult, Model model) {
    logger.info("Remove book");
    bookToRemoveValidator.validate(bookToRemove, bindingResult);
    if (bindingResult.hasErrors()) {
      logger.info("Incorrect bookToRemove " + bookToRemove);
      prepareModel(model, null, bookToRemove, null, null);
      return "books_shelf";
    }
    if (!bookService.removeBook(bookToRemove)) {
      logger.info("Deleted no books");
      bindingResult.reject("notfound", "Book not found");
      prepareModel(model, null, bookToRemove, null, null);
      return "books_shelf";
    }
    return "redirect:/books/shelf";
  }

  @PostMapping("/uploadFile")
  public String uploadFile(
      @RequestParam("file") MultipartFile file,
      Model model
  ) throws Exception {
    String errorMessage = fileValidator.validate(file);
    if(!errorMessage.isEmpty()) {
      UploadFile uploadFile = new UploadFile();
      uploadFile.setErrorMessage(errorMessage);
      if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
        uploadFile.setFile(file);
      }
      logger.info(uploadFile.getErrorMessage());
      prepareModel(model, null, null, uploadFile, null);
      return "books_shelf";
    }

    File serverFile = fileService.getServerFile(file.getOriginalFilename());
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
    try {
      stream.write(file.getBytes());
    } finally {
      stream.close();
    }

    logger.info("File " + serverFile.getAbsolutePath() + " uploaded successfully");
    UploadFile uploadFile = new UploadFile();
    uploadFile.setInfoMessage("File " + serverFile.getName() + " uploaded successfully");
    prepareModel(model, null, null, uploadFile, null);
    return "books_shelf";
  }

  @RequestMapping("/file/{name:.+}")
  public void getFile(@PathVariable("name") String name, HttpServletResponse response) throws Exception {
    File serverFile = fileService.getServerFile(name);

    if (serverFile.exists()) {
      FileInputStream fsIo = new FileInputStream(serverFile);
      try {
        response.addHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.addHeader("Content-Length", String.valueOf(serverFile.length()));
        response.addHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        IOUtils.copy(fsIo, response.getOutputStream());
        response.getOutputStream().flush();
      } finally {
        fsIo.close();
      }
    }
    throw new Exception("File " + name + " not found");
  }
}
