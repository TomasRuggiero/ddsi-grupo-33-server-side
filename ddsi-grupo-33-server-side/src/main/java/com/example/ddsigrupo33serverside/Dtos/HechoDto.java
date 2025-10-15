package com.example.ddsigrupo33serverside.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class HechoDto {
  public UUID id;
  public String titulo;
  private String fecha_acontecimiento;
  private String ubicacion;
  private String fuente;
  private Set<String> categorias;
}
