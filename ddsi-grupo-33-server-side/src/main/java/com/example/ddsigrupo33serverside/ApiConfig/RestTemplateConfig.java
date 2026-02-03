package com.example.ddsigrupo33serverside.ApiConfig;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() {
    // Connection manager para controlar conexiones
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(50);       // conexiones totales
    connectionManager.setDefaultMaxPerRoute(20); // conexiones por ruta

    CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(connectionManager)
        .build();

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory(httpClient);

    // Opcional: timeouts para uploads grandes
    requestFactory.setConnectTimeout(30_000); // 30s
    requestFactory.setReadTimeout(300_000);   // 5min

    return new RestTemplate(requestFactory);
  }
}
