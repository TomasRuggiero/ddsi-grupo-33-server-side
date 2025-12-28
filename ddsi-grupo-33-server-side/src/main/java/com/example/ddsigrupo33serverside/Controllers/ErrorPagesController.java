package com.example.ddsigrupo33serverside.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPagesController {

  @GetMapping("/error404")
  public String notFound() {
    return "error404";
  }
}
