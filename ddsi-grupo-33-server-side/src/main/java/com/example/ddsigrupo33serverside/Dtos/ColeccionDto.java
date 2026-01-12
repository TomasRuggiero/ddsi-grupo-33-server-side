package com.example.ddsigrupo33serverside.Dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColeccionDto {
  private Long Id;
  private String Titulo;
  private String Descripcion;
  private CriterioDePertenenciaDto CriterioDePertenencia;
  private String algoritmoDeConsenso;
  private List<HechoDto> Hechos;
  private Integer TotalDeHechos;
}
