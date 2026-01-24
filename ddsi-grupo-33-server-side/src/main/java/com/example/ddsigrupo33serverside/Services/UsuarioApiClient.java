package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.AdminUsersDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioApiClient {
    private final RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/user";

    public List<AdminUsersDto> listarUsuarios(String nombre, String correo, String rol) {
        ParameterizedTypeReference<List<AdminUsersDto>> typeRef = new ParameterizedTypeReference<List<AdminUsersDto>>() {};

        String uri = BASE_URL + "?";

        if (!Strings.isBlank(nombre))
            uri += "nombre=" + nombre;
        if (!Strings.isBlank(correo))
            uri += "&correo=" + correo;
        if (!Strings.isBlank(rol))
            uri += "&rol=" + rol;

        ResponseEntity<List<AdminUsersDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                typeRef
        );

        return response.getBody();
    }
}
