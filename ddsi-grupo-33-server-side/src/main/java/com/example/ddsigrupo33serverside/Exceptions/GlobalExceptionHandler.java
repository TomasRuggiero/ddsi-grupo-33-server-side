package com.example.ddsigrupo33serverside.Exceptions;

import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import com.example.ddsigrupo33serverside.Dtos.UbicacionDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpClient;

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

  @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
  public ModelAndView handleUnauthorizedException(HttpClientErrorException.Unauthorized ex) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("error401");
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

    HechoDto hecho = new HechoDto();
    hecho.setUbicacion(new UbicacionDto()); // 👈 CLAVE

    model.addAttribute("hecho", hecho);
    model.addAttribute(
        "error",
        "No se pudieron procesar los archivos subidos (máx. 5)"
    );

    return "subir";
  }


}