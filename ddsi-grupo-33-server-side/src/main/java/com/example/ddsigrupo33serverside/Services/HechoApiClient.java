package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HechoApiClient {

  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://localhost:8080/hechos";

  public List<HechoDto> getMisHechos() {
      ParameterizedTypeReference<List<HechoDto>> typeRef = new ParameterizedTypeReference<List<HechoDto>>() {};

      ResponseEntity<List<HechoDto>> response = restTemplate.exchange(
              "http://localhost:8080/user/hechos-subidos",
              HttpMethod.GET,
              null,
              typeRef
      );

      return response.getBody();
  }

  public HechoDto getHechoPorId(UUID id) {
    return restTemplate.getForObject(BASE_URL + "/" + id, HechoDto.class);
  }

  public List<HechoDto> getAllHechos() {
      ParameterizedTypeReference<List<HechoDto>> typeRef = new ParameterizedTypeReference<List<HechoDto>>() {};

      ResponseEntity<List<HechoDto>> response = restTemplate.exchange(
              BASE_URL,
              HttpMethod.GET,
              null,
              typeRef
      );

      return response.getBody();
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

    if (archivos.size() > 5) {
      throw new IllegalArgumentException("No se pueden subir más de 5 archivos");
    }

    for (MultipartFile file : archivos) {

      HttpHeaders partHeaders = new HttpHeaders();
      partHeaders.setContentType(MediaType.parseMediaType(file.getContentType()));

      HttpEntity<ByteArrayResource> part = new HttpEntity<>(
          new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
              return file.getOriginalFilename();
            }
          },
          partHeaders
      );

      body.add("archivos", part);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    HttpEntity<MultiValueMap<String, Object>> requestEntity =
        new HttpEntity<>(body, headers);

    restTemplate.postForEntity(
        BASE_URL + "/" + id + "/multimedia",
        requestEntity,
        Void.class
    );
  }

  public void eliminarHecho(UUID id) {
      restTemplate.delete(BASE_URL + "/" + id);

  }

    public void aceptar(UUID id, String sugerencia) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        params.put("sugerencia", sugerencia != null ? sugerencia : "");

        restTemplate.postForObject(
                BASE_URL + "/{id}/aceptar?sugerencia={sugerencia}",
                null,
                Void.class,
                params
        );
    }

    public void rechazar(UUID id, String sugerencia) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        params.put("motivo", sugerencia != null ? sugerencia : "Sin motivo especificado");

        restTemplate.postForObject(
                BASE_URL + "/{id}/rechazar?motivo={motivo}",
                null,
                Void.class,
                params
        );
    }
}
