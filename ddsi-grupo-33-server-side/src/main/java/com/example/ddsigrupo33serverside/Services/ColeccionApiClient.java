package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.ColeccionInputDto;
import com.example.ddsigrupo33serverside.Dtos.FiltroHechosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@RequiredArgsConstructor
public class ColeccionApiClient {

  private final RestTemplate restTemplate;

  // URL base del backend
  private static final String BASE_URL = "http://localhost:8080/colecciones";

  public void crearColeccion(ColeccionInputDto coleccionInputDto) {
    restTemplate.postForObject(BASE_URL, coleccionInputDto, Void.class);
  }

  public void eliminarColeccion(Long id) {
    restTemplate.delete(BASE_URL + "/" + id);
  }

  public List<ColeccionDto> getTodasLasColecciones() {
    ColeccionDto[] colecciones = restTemplate.getForObject(BASE_URL, ColeccionDto[].class);
    List<ColeccionDto> coleccionesLista = Arrays.asList(colecciones);

    coleccionesLista.forEach(c -> c.setTotalDeHechos(c.getHechos().size()));

    return coleccionesLista;
  }

  public void actualizarColeccion(ColeccionInputDto coleccionInputDto, Long id, String algoritmoDeConsenso) {
    restTemplate.put(BASE_URL + "/" + id, coleccionInputDto);
    restTemplate.put(BASE_URL + "/" + id + "/consenso/" + algoritmoDeConsenso, null);
  }

  public ColeccionDto getColeccionPorId(Long id, FiltroHechosDto filtros) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/" + id);

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
