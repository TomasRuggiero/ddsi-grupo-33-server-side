package com.example.ddsigrupo33serverside.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpClientErrorException.NotFound.class)
  public ModelAndView handleNotFoundException(HttpClientErrorException.NotFound ex) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("error404");
    return mav;
  }

  @ExceptionHandler(HttpClientErrorException.Forbidden.class)
  public ModelAndView handleForbiddenException(HttpClientErrorException ex) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("error403");
    return mav;
  }
}