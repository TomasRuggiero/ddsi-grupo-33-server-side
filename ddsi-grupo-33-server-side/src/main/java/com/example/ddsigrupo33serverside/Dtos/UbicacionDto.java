package com.example.ddsigrupo33serverside.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionDto {
  private String direccion;
  private String ciudad;
  private String provincia;
  private String pais;
  private String longitud;
  private String latitud;
}
