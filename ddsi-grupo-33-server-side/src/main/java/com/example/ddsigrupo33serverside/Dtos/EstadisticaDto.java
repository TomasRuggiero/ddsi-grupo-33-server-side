package com.example.ddsigrupo33serverside.Dtos;

import java.util.List;
import lombok.Data;

@Data
public class EstadisticaDto {

  private List<EstadisticaDetalleDto> coleccionProvincia;
  private List<EstadisticaDetalleDto> categoriaProvincia;
  private List<EstadisticaDetalleDto> categoriaHora;
  private String categoriaConMasHechos;
  private String solicitudesSpam;

}
