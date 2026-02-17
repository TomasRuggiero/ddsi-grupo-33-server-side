package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.ColeccionInputDto;
import com.example.ddsigrupo33serverside.Dtos.FiltroHechosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@RequiredArgsConstructor
public class ColeccionApiClient {

  private final RestTemplate restTemplate;

  @Value("${backend.api.url}")
  private String BASE_URL;

  public void crearColeccion(ColeccionInputDto coleccionInputDto, String algoritmoDeConsenso) {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody = restTemplate.postForObject(BASE_URL + "/colecciones", coleccionInputDto, Map.class);
    restTemplate.put(BASE_URL + "/colecciones" + "/" + responseBody.get("id").toString() + "/consenso/" + algoritmoDeConsenso, null);
  }

  public void eliminarColeccion(Long id) {
    restTemplate.delete(BASE_URL + "/colecciones" + "/" + id);
  }

  public List<ColeccionDto> getTodasLasColecciones() {
    ColeccionDto[] colecciones = restTemplate.getForObject(BASE_URL + "/colecciones", ColeccionDto[].class);
    List<ColeccionDto> coleccionesLista = Arrays.asList(colecciones);

    return coleccionesLista;
  }

  public void actualizarColeccion(ColeccionInputDto coleccionInputDto, Long id, String algoritmoDeConsenso) {
    restTemplate.put(BASE_URL + "/colecciones" + "/" + id, coleccionInputDto);
    restTemplate.put(BASE_URL + "/colecciones" + "/" + id + "/consenso/" + algoritmoDeConsenso, null);
  }

  public ColeccionDto getColeccionPorId(Long id, FiltroHechosDto filtros, Boolean curado) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/colecciones" + "/" + id);

    if (curado != null && curado)
      builder.queryParam("curado", true);
    else
      builder.queryParam("curado", false);
    if (filtros.getFecha_desde() != null) {
      builder.queryParam("fecha_desde", new SimpleDateFormat("yyyy-MM-dd").format(filtros.getFecha_desde()));
    }
    if (filtros.getFecha_hasta() != null) {
      builder.queryParam("fecha_hasta", new SimpleDateFormat("yyyy-MM-dd").format(filtros.getFecha_hasta()));
    }
    if (!isBlank(filtros.getLatitud())) {
      builder.queryParam("latitud", filtros.getLatitud());
    }
    if (!isBlank(filtros.getLongitud())) {
      builder.queryParam("longitud", filtros.getLongitud());
    }
    if (!isBlank(filtros.getTitulo())) {
      builder.queryParam("titulo", filtros.getTitulo());
    }
    if (!isBlank(filtros.getCategoria())) {
      builder.queryParam("categoria", filtros.getCategoria());
    }

    return restTemplate.getForObject(builder.toUriString(), ColeccionDto.class);
  }

}
