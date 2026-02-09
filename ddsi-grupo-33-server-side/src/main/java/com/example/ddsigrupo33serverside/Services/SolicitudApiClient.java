package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.SolicitudDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudApiClient {

  private final RestTemplate restTemplate;
  
  @Value("${BACKEND_URL:http://localhost:8080}")
  private String BASE_URL;

  public SolicitudDto crearSolicitud(SolicitudDto solicitud) {
    return restTemplate.postForObject(BASE_URL + "/solicitudes", solicitud, SolicitudDto.class);
  }

  public void aceptar(Integer id) {
    restTemplate.put(BASE_URL + "/solicitudes" + "/aprobada/" + id, null);
  }

  public void rechazar(Integer id) {
    restTemplate.put(BASE_URL + "/solicitudes" + "/rechazada/" + id, null);
  }

  public List<SolicitudDto> getAllSolicitudes() {
    return restTemplate.getForObject(BASE_URL + "/solicitudes", List.class);
  }

}
