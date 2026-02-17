package com.example.ddsigrupo33serverside.Dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColeccionPagedDto {
  private Long Id;
  private String Titulo;
  private String Descripcion;
  private CriterioDePertenenciaDto CriterioDePertenencia;
  private String algoritmoDeConsenso;
  private PageResponseDto<HechoDto> Hechos;
  private Integer totalDeHechos;
  @JsonProperty("fuentes_id")
  private List<Long> idsFuentesDeDatos;
}
