package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.SolicitudDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SolicitudApiClient {

  private final RestTemplate restTemplate;
  private static final String BASE_URL = "http://localhost:8080/solicitudes";

  public SolicitudDto crearSolicitud(SolicitudDto solicitud) {
    return restTemplate.postForObject(BASE_URL, solicitud, SolicitudDto.class);
  }

}
