package com.example.ddsigrupo33serverside.Dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColeccionDto {
  public Long id;
  public String titulo;
  public String descripcion;
  public List<HechoDto> hechos;
  public Integer totalDeHechos;
}
