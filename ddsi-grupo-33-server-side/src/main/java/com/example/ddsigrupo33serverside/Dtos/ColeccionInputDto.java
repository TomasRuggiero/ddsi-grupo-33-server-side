package com.example.ddsigrupo33serverside.Dtos;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ColeccionInputDto {
  private String titulo;
  private String descripcion;
  @JsonProperty("hechos_id")
  private List<UUID> idHechos;
  private CriterioDePertenenciaInputDto criterioDePertenencia;
  @JsonProperty("fuentes_id")
  private List<Long> idsFuentesDeDatos;
}
