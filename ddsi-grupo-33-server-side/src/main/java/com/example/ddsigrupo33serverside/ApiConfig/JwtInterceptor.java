package com.example.ddsigrupo33serverside.ApiConfig;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

public class JwtInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    // Obtenemos los atributos de la petición actual que fluye por el front liviano
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (attributes != null) {
      // Buscamos la sesión (sin crear una nueva si no existe)
      HttpSession session = attributes.getRequest().getSession(false);

      if (session != null) {
        // CAMBIO AQUÍ: Usamos "accessToken" que es como lo guarda tu CustomAuthProvider
        String token = (String) session.getAttribute("accessToken");

        if (token != null && !token.isEmpty()) {
          // Inyectamos el header para que el Backend real te autorice
          request.getHeaders().setBearerAuth(token);
        }
      }
    }

    return execution.execute(request, body);
  }
}