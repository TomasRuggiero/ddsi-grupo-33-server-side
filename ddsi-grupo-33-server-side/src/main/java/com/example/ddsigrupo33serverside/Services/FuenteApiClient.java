package com.example.ddsigrupo33serverside.Services;

import java.util.List;

import com.example.ddsigrupo33serverside.Dtos.FuenteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FuenteApiClient {

  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://localhost:8080/fuentes";

  public List<FuenteDto> getAllFuentes() {
    return restTemplate.getForObject(BASE_URL, List.class);
  }
}
