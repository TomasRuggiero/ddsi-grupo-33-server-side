package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.EstadisticaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EstadisticaService {

  private static final Logger log = LoggerFactory.getLogger(GestionUsuariosApiService.class);
  private final WebClient webClient;

  @Autowired
  public EstadisticaService(
      WebApiCallerService webApiCallerService,
      @Value("${backend.api.url}") String authServiceUrl){
    this.webClient = WebClient.builder().
        baseUrl(authServiceUrl)
        .build();
  }

  public EstadisticaDto obtenerEstadistica(){
    return webClient.get().uri("/estadisticas").retrieve().bodyToMono(EstadisticaDto.class).block();
  }

}
