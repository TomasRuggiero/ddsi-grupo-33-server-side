package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.AuthResponseDto;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class GestionUsuariosApiService {

  private static final Logger log = LoggerFactory.getLogger(GestionUsuariosApiService.class);
  private final WebClient webClient;
  private final WebApiCallerService webApiCallerService;
  private final String authServiceUrl;

  @Autowired
  public GestionUsuariosApiService(
      WebApiCallerService webApiCallerService,
      @Value("${auth.service.url}") String authServiceUrl){
    this.webClient = WebClient.builder().build();
    this.webApiCallerService = webApiCallerService;
    this.authServiceUrl = authServiceUrl;
  }

  public AuthResponseDto login(String username, String password) {
    try {
      AuthResponseDto response = webClient
          .post()
          .uri(authServiceUrl + "/auth")
          .bodyValue(Map.of(
              "username", username,
              "password", password
          ))
          .retrieve()
          .bodyToMono(AuthResponseDto.class)
          .block();
      return response;
    } catch (WebClientResponseException e) {
      log.error(e.getMessage());
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        // Login fallido - credenciales incorrectas
        return null;
      }
      // Otros errores HTTP
      throw new RuntimeException("Error en el servicio de autenticación: " + e.getMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Error de conexión con el servicio de autenticación: " + e.getMessage(), e);
    }
  }

}
