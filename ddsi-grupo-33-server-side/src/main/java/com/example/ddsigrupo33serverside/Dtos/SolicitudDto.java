package com.example.ddsigrupo33serverside.Dtos;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class SolicitudDto {

  private Integer id;
  private UUID idHecho;
  private String tituloHecho;
  private String justificacion;
  private String estado;
  private Date fechaDeCarga;

}