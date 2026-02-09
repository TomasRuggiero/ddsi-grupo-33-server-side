package com.example.ddsigrupo33serverside.Services;

import java.util.List;

import com.example.ddsigrupo33serverside.Dtos.FuenteDto;
import com.example.ddsigrupo33serverside.Dtos.FuenteProxyInputDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FuenteApiClient {

  private final RestTemplate restTemplate;

  @Value("${BACKEND_URL:http://localhost:8080}")
  private String BASE_URL;

  public void eliminar(Long id) {
    try {
      restTemplate.delete(BASE_URL + "/fuentes" + "/" + id);
    } catch (Exception e) {
      System.out.println("Error al eliminar la fuente " + id);
      throw e;
    }
  }

  public List<FuenteDto> getAllFuentes() {
    ParameterizedTypeReference<List<FuenteDto>> typeRef = new ParameterizedTypeReference<List<FuenteDto>>() {};

    ResponseEntity<List<FuenteDto>> response = restTemplate.exchange(
        BASE_URL + "/fuentes",
        HttpMethod.GET,
        null,
        typeRef
    );

    return response.getBody();
  }

  public void crearFuenteDinamica() {
    restTemplate.postForEntity(BASE_URL + "/fuentes" + "/dinamica", null, String.class);
  }

  public void crearFuenteEstatica(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("El archivo no puede estar vacío");
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("archivo", file.getResource());

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    restTemplate.postForEntity(BASE_URL + "/fuentes" + "/estatica", requestEntity, String.class);
  }

  public void crearFuenteProxy(FuenteProxyInputDto dto) {
    try {
      ResponseEntity<String> response = restTemplate.postForEntity(
          BASE_URL + "/fuentes" + "/proxy",
          dto,
          String.class
      );

      if (response.getStatusCode().is2xxSuccessful()) {
        System.out.println("Fuente Proxy creada con éxito en el servidor remoto");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error al conectar con el servidor de fuentes: " + e.getMessage());
    }
  }
}