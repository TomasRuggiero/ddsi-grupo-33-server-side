package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColeccionApiClient {

  private final RestTemplate restTemplate;

  // URL base del backend
  private static final String BASE_URL = "http://localhost:8080/colecciones";

  public List<ColeccionDto> getTodasLasColecciones() {
    ColeccionDto[] colecciones = restTemplate.getForObject(BASE_URL, ColeccionDto[].class);
    List<ColeccionDto> coleccionesLista = Arrays.asList(colecciones);

    coleccionesLista.forEach(c -> c.setTotalDeHechos(c.getHechos().size()));

    return coleccionesLista;
  }

  public ColeccionDto getColeccionPorId(Long id) {
    return restTemplate.getForObject(BASE_URL + "/" + id, ColeccionDto.class);
  }

}
