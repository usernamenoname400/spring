package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfException;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/login")
public class LoginController {

  private final Logger logger = Logger.getLogger(LoginController.class);

  @GetMapping
  public ModelAndView login(Model model) {
    logger.info("GET /login returns login_form.html");
    model.addAttribute("loginForm", new LoginForm());
    return new ModelAndView("login_form");
  }

  @PostMapping("/auth")
  public String authenticate(LoginForm loginForm) throws BookShelfException {
    /*if (loginService.authenticate(loginForm)) {
      logger.info("Login OK, redirect to book shelf");
      return "redirect:/books/shelf";
    } else {
      logger.warn("Login failed");
      throw new BookShelfException("Invalid login or password");
    }*/
    return "";
  }

  @ExceptionHandler(BookShelfException.class)
  public String handleBookShelfException(Model model, BookShelfException ex) {
    logger.info("raised BookShelfException, show custom page");
    model.addAttribute("errorMessage", ex.getMessage());
    return "errors/404";
  }
}
