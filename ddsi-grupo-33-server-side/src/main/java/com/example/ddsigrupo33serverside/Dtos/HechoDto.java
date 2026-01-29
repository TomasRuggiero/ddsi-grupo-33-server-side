package com.example.ddsigrupo33serverside.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HechoDto {
  private UUID id;
  private String titulo;
  private String descripcion;
  @JsonProperty("fecha")
  private LocalDate fecha_acontecimiento;
  @JsonProperty("fechaDeCarga")
  private LocalDate fecha_de_carga;
  private String estado;
  private String fuente;
  private String origen;
  private Set<String> categorias;
  private UbicacionDto ubicacion;
}
