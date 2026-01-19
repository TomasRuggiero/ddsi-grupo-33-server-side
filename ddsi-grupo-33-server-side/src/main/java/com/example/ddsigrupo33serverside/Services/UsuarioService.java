package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.UsuarioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final GestionUsuariosApiService gestionUsuariosApiService;

  public void crearUsuario(UsuarioDto usuarioDto){

    gestionUsuariosApiService.crearUsuario(usuarioDto);

  }
}
