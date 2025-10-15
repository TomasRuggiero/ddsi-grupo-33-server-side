package com.example.ddsigrupo33serverside.Services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import org.springframework.stereotype.Service;

@Service
public class ColeccionService {
  public List<ColeccionDto> getTodasLasColecciones() {
      List<HechoDto> hechosIncendios = List.of(
          new HechoDto(
              UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
              "Incendio en Córdoba",
              "2024-12-03",
              "Córdoba, Argentina",
              "La Voz del Interior",
              Set.of("Incendio", "Medioambiente")
          ),
          new HechoDto(
              UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
              "Reforestación en La Cumbre",
              "2025-01-20",
              "La Cumbre, Córdoba",
              "Agencia Télam",
              Set.of("Reforestación", "Voluntariado")
          )
      );

      List<HechoDto> hechosInundaciones = List.of(
          new HechoDto(
              UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
              "Inundación en Santa Fe",
              "2024-11-12",
              "Santa Fe, Argentina",
              "El Litoral",
              Set.of("Inundación", "Clima Extremo")
          ),
          new HechoDto(
              UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
              "Reparación de defensas",
              "2025-02-05",
              "Santa Fe, Argentina",
              "Ministerio de Obras Públicas",
              Set.of("Infraestructura", "Recuperación")
          )
      );

      List<HechoDto> hechosContaminacion = List.of(
          new HechoDto(
              UUID.fromString("550e8400-e29b-41d4-a716-446655440004"),
              "Contaminación en el Riachuelo",
              "2025-03-10",
              "Buenos Aires, Argentina",
              "Página 12",
              Set.of("Contaminación", "Río", "Industria")
          )
      );

      List<ColeccionDto> colecciones = List.of(
          new ColeccionDto(1l, "Incendios Forestales", "Eventos relacionados con incendios en zonas rurales y boscosas.", hechosIncendios, hechosIncendios.size()),
          new ColeccionDto(2l, "Inundaciones Urbanas", "Mapeo de eventos recientes de inundaciones en distintas provincias.", hechosInundaciones, hechosInundaciones.size()),
          new ColeccionDto(3l, "Contaminación Ambiental", "Hechos que documentan la contaminación de ríos, suelos y aire.", hechosContaminacion, hechosContaminacion.size())
      );

      return colecciones;
  };

  public ColeccionDto getColeccionPorId(Long id) {
    return getTodasLasColecciones().stream()
        .filter(c -> c.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public HechoDto getHechoPorId(UUID id) {
    return getTodasLasColecciones().stream()
        .flatMap(c -> c.getHechos().stream())
        .filter(h -> h.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

}
