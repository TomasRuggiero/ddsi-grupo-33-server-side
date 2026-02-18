package com.example.ddsigrupo33serverside.Exceptions;

import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import com.example.ddsigrupo33serverside.Dtos.UbicacionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.http.HttpClient;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({HttpClientErrorException.NotFound.class, NoResourceFoundException.class})
  public ModelAndView handleNotFoundException(RuntimeException ignored) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("error/404");
    return mav;
  }

  @ExceptionHandler(HttpClientErrorException.Forbidden.class)
  public ModelAndView handleForbiddenException(HttpClientErrorException ex) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("error/403");
    return mav;
  }

  @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
  public ModelAndView handleUnauthorizedException(HttpClientErrorException.Unauthorized ex) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("error/401");
    return mav;
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public String handleMaxUploadSize(
      MaxUploadSizeExceededException ex,
      Model model
  ) {
    HechoDto hecho = new HechoDto();
    hecho.setUbicacion(new UbicacionDto());

    model.addAttribute("hecho", hecho);
    model.addAttribute(
        "errorMaxUpload",
        "Uno o más archivos superan el tamaño máximo permitido (5MB)"
    );
    return "subir";
  }

  @ExceptionHandler(Exception.class)
  public String handleAny(Exception ex, Model model) {
    ex.printStackTrace();

    return "error/500";
  }


}