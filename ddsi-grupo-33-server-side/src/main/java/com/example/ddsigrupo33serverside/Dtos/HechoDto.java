package com.example.ddsigrupo33serverside.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
  @DateTimeFormat(pattern = "yyyy-MM-dd") // Esto soluciona el binding del formulario web  private Date fecha;
  private Date fecha;
  private String fuente;
  private Set<String> categorias;
  private UbicacionDto ubicacion;
}
