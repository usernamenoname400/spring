package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
  Logger logger = Logger.getLogger(this.getClass());

  @GetMapping("/404")
  public String notFoundError() {
    logger.info("Page not found, show custom page");
    return "errors/404";
  }
}
