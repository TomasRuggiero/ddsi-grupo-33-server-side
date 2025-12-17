package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HechoApiClient {

  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://localhost:8080/hechos";

  public HechoDto getHechoPorId(UUID id) {
    return restTemplate.getForObject(BASE_URL + "/" + id, HechoDto.class);
  }

  public List<HechoDto> getAllHechos() {
    return restTemplate.getForObject(BASE_URL, List.class);
  }

  public UUID crearHecho(HechoDto hechoDto) {
    try {
      HechoDto respuestaHecho = restTemplate.postForObject(BASE_URL, hechoDto, HechoDto.class);

      if (respuestaHecho != null) {
        return respuestaHecho.getId();
      } else {
        throw new RuntimeException("Error: La API no devolvió el objeto creado.");
      }

    } catch (Exception e) {
      throw new RuntimeException("Error al crear el hecho vía API: " + e.getMessage());
    }
  }

  public void solicitarEliminacion(UUID id, String justificacion) {
  }

  public void agregarMultimedia(UUID id, List<MultipartFile> archivos) throws IOException {
     MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
     for (MultipartFile file : archivos) {
         body.add("archivos", new ByteArrayResource(file.getBytes()) {
             @Override
             public String getFilename() {
                 return file.getOriginalFilename();
             }
         });
     }

     try {
       restTemplate.postForEntity(BASE_URL + "/" + id + "/multimedia", body, Void.class);
     }
     catch (Exception e) {
       throw new RuntimeException("Error al agregar multimedia: " + e.getMessage());
     }
  }
}
