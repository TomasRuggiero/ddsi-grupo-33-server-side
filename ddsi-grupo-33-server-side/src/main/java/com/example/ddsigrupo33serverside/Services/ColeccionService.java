package com.example.ddsigrupo33serverside.Services;

import java.io.NotActiveException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import com.example.ddsigrupo33serverside.Dtos.UbicacionDto;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

@Service
public class ColeccionService {
  public List<ColeccionDto> getAllColecciones() {
    //TODO: Hacer
    return List.of();
  }

  public ColeccionDto getColeccionPorId(Long id) {
    return getAllColecciones().stream()
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  public HechoDto getHechoPorId(UUID id) {
    return getAllColecciones().stream()
        .flatMap(c -> c.getHechos().stream())
        .filter(h -> h.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

}
