package com.example.ddsigrupo33serverside.Services;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import com.example.ddsigrupo33serverside.Dtos.UbicacionDto;
import org.springframework.stereotype.Service;

@Service
public class ColeccionService {
  public List<ColeccionDto> getTodasLasColecciones() {
    List<HechoDto> hechosIncendios = List.of(
        new HechoDto(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            "Incendio en Córdoba",
            "Incendio",
            new Date(2024/12/3),
            "Estática",
            Set.of("Incendio", "Medioambiente"),
            new UbicacionDto("Calle falsa 123","Villa Carlos Paz","Cordoba","Argentina","-64.1833","-31.4167")),
        new HechoDto(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
            "Reforestación en La Cumbre",
            "Reforestacion",
            new Date(2025/1/20),
            "Dinámica",
            Set.of("Reforestación", "Voluntariado"),
            new UbicacionDto("Calle falsa 345","La Cumbre","Cordoba","Argentina","-64.1833","-31.4167")
    ));

    List<HechoDto> hechosInundaciones = List.of(
        new HechoDto(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
            "Inundación en Santa Fe",
            "Inundacion",
            new Date(2024/11/12),
            "Proxy",
            Set.of("Inundación", "Clima Extremo"),
            new UbicacionDto("Calle falsa 567","Rosario","Santa Fe","Argentina","-64.1833","-31.4167")
        ),
        new HechoDto(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
            "Reparación de defensas",
            "Reparacion",
            new Date(2025/2/5),
            "Estática",
            Set.of("Infraestructura", "Recuperación"),
            new UbicacionDto("Calle falsa 789","Rosario","Santa Fe","Argentina","-64.1833","-31.4167")
        )
    );

    List<HechoDto> hechosContaminacion = List.of(
        new HechoDto(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440004"),
            "Contaminación en el Riachuelo",
            "Contaminacion",
            new Date(2025/3/10),
            "Proxy",
            Set.of("Contaminación", "Río", "Industria"),
            new UbicacionDto("Calle falsa 912","Buenos Aires","Buenos Aires","Argentina","-64.1833","-31.4167")
        )
    );

    List<ColeccionDto> colecciones = List.of(
        new ColeccionDto(1L, "Incendios Forestales", "Eventos relacionados con incendios en zonas rurales y boscosas.", hechosIncendios, hechosIncendios.size()),
        new ColeccionDto(2L, "Inundaciones Urbanas", "Mapeo de eventos recientes de inundaciones en distintas provincias.", hechosInundaciones, hechosInundaciones.size()),
        new ColeccionDto(3L, "Contaminación Ambiental", "Hechos que documentan la contaminación de ríos, suelos y aire.", hechosContaminacion, hechosContaminacion.size())
    );

    return colecciones;
  }

  public ColeccionDto getColeccionPorId(Long id) {
    return getTodasLasColecciones().stream()
        .filter(c -> c.getId().equals(id))
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
