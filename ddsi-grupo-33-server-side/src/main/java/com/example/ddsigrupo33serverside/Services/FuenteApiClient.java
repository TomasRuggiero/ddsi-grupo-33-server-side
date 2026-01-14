package com.example.ddsigrupo33serverside.Services;

import java.util.List;

import com.example.ddsigrupo33serverside.Dtos.FuenteDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FuenteApiClient {

  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://localhost:8080/fuentes";

  public List<FuenteDto> getAllFuentes() {
    ParameterizedTypeReference<List<FuenteDto>> typeRef = new ParameterizedTypeReference<List<FuenteDto>>() {};

    ResponseEntity<List<FuenteDto>> response = restTemplate.exchange(
        BASE_URL,
        HttpMethod.GET,
        null,
        typeRef
    );

    return response.getBody();
  }
}