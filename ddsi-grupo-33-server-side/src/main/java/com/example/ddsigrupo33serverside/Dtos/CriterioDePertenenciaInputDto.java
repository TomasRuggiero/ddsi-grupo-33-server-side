package com.example.ddsigrupo33serverside.Dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CriterioDePertenenciaInputDto {@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
public Date fecha_desde;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  public Date fecha_hasta;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  public Date fecha_acontecimiento_desde;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  public Date fecha_acontecimiento_hasta;
  public String latitud;
  public String longitud;
  public List<String> categorias;

}
