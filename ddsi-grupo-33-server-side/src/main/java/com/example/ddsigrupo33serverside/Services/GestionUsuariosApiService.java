package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.AuthResponseDto;
import com.example.ddsigrupo33serverside.Dtos.UsuarioDto;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  public AuthResponseDto login(String correo, String password) {
    try {
      AuthResponseDto response = webClient
          .post()
          .uri(authServiceUrl + "/auth")
          .bodyValue(Map.of(
              "correo", correo,
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

  public void crearUsuario(UsuarioDto usuarioDto){
    //ResponseEntity response = webApiCallerService.post(authServiceUrl + "/user", usuarioDto, UsuarioDto.class);
    try {
      ResponseEntity response = webClient
          .post()
          .uri(authServiceUrl + "/user")
          .bodyValue(usuarioDto)
          .retrieve()
          .toBodilessEntity()
          .block();

      if (response.getStatusCode() == null) {
        throw new RuntimeException("Error al crear alumno en el servicio externo");
      }
    } catch (WebClientResponseException e) {
      log.error(e.getMessage());
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        //return null;
      }
      // Otros errores HTTP
      throw new RuntimeException("Error en el servicio de autenticación: " + e.getMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Error de conexión con el servicio de autenticación: " + e.getMessage(), e);
    }
  }

  /*
      public AuthResponseDTO login(String username, String password) {
        try {
            AuthResponseDTO response = webClient
                    .post()
                    .uri(authServiceUrl + "/auth")
                    .bodyValue(Map.of(
                            "username", username,
                            "password", password
                    ))
                    .retrieve()
                    .bodyToMono(AuthResponseDTO.class)
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
   */

}
