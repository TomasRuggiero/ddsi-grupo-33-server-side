package com.example.ddsigrupo33serverside.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class AuthController {

  @GetMapping("/login")
  public String login(Authentication authentication, Model model) {
    model.addAttribute("titulo", "Login");
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/";
    }
    return "login";
  }
}
