package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.UserService;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/user")
public class UserController {
  private final Logger logger = Logger.getLogger(BooksShelfController.class);
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/edit")
  public String userEdit(Model model) {
    logger.info("open edit form");
    model.addAttribute("user", new User());
    return "user_form";
  }

  @PostMapping("/save")
  public String saveBook(User user) {
    if (userService.saveUser(user)) {
      return "redirect:/login";
    }
    return "redirect:/user/edit";
  }

  @PostMapping("/remove")
  public String removeBook(@RequestParam(value="userIdToRemove") String userIdToRemove) {
    userService.removeUserById(userIdToRemove);
    return "redirect:/login";
  }
}
