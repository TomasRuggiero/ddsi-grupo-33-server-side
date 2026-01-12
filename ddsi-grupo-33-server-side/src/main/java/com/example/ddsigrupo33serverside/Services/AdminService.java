package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {
  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://localhost:8080";
  private final HechoApiClient hechoApiClient;
  private final FuenteApiClient fuenteApiClient;
  private final ColeccionApiClient coleccionApiClient;
  private final SolicitudApiClient solicitudApiClient;

  public AdminHomeDto getAdminHome() {
        List<HechoDto> todosLosHechos = hechoApiClient.getAllHechos();

        long sieteDiasEnMillis = 7L * 24 * 60 * 60 * 1000;
        Date limiteHaceUnaSemana = new Date(System.currentTimeMillis() - sieteDiasEnMillis);
        List<HechoDto> hechosUltimaSemana = todosLosHechos.stream()
              .filter(h -> h.getFecha_acontecimiento().after(limiteHaceUnaSemana)
                      && h.getFecha_acontecimiento().before(new Date()))
              .toList();

        Integer totalColecciones = coleccionApiClient.getTodasLasColecciones().size();
        Integer totalSolicitudes = solicitudApiClient.getAllSolicitudes().size();
        // TODO: Obtener usuarios
        Integer totalUsuarios = 2;

        return new AdminHomeDto(todosLosHechos, hechosUltimaSemana, todosLosHechos.size(), totalColecciones, totalSolicitudes, totalUsuarios);
    }

    public List<ColeccionDto> getAdminColecciones() {
        return coleccionApiClient.getTodasLasColecciones();
    }

    public List<FuenteDto> getAllFuentes() {
      return fuenteApiClient.getAllFuentes();
    }

    public List<HechoDto> getAdminHechos() {
      return hechoApiClient.getAllHechos();
    }

    public List<SolicitudDto> getSolicitudes() {
      SolicitudDto[] solicitudes = restTemplate.getForObject(BASE_URL + "/solicitudes", SolicitudDto[].class);
      return Arrays.asList(solicitudes);
    }
}
