package com.example.ddsigrupo33serverside.Dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class SolicitudDto {
  private UUID idHecho;
  private String justificacion;
}
