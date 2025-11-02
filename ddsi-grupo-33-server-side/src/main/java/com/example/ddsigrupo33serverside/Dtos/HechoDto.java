package com.example.ddsigrupo33serverside.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date fecha;
  private String fuente;
  private Set<String> categorias;
  private UbicacionDto ubicacion;
}
