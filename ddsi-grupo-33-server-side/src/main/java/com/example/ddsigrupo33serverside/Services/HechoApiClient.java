package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HechoApiClient {

  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://localhost:8080/hechos";

  public HechoDto getHechoPorId(UUID id) {
    return restTemplate.getForObject(BASE_URL + "/" + id, HechoDto.class);
  }

}
