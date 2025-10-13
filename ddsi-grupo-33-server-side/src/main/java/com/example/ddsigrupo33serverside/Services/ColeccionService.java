package com.example.ddsigrupo33serverside.Services;

import java.util.ArrayList;
import java.util.List;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import org.springframework.stereotype.Service;

@Service
public class ColeccionService {
  public List<ColeccionDto> getTodasLasColecciones() {
    List<ColeccionDto> colecciones = new ArrayList<>();

    List<HechoDto> hechos = new ArrayList<>();
    hechos.add(new HechoDto("Titulo de prueba"));
    hechos.add(new HechoDto("Titulo de prueba 2"));

    colecciones.add(new ColeccionDto("Titulo de prueba", "Esto es una descripcion de prueba", hechos, 1));
    colecciones.add(new ColeccionDto("Titulo de prueba 2", "Esto es una descripcion de prueba 2", hechos, 2));
    colecciones.add(new ColeccionDto("Titulo de prueba 3", "Esto es una descripcion de prueba 3", hechos, 3));

    return colecciones;
  };
}
