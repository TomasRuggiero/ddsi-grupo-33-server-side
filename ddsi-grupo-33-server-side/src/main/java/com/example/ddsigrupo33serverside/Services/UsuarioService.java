package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.UsuarioDto;
import com.example.ddsigrupo33serverside.Exceptions.DuplicateCorreoException;
import com.example.ddsigrupo33serverside.Exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

  private final GestionUsuariosApiService gestionUsuariosApiService;

  public void crearUsuario(UsuarioDto usuarioDto){

    log.info(String.format("Iniciando crear usuario %s", usuarioDto.getNombre()));
    validarDatosBasicos(usuarioDto);
    log.info("Usuario validado");
    validarUsuarioExistente(usuarioDto);
    log.info("Usuario validado sin repeticion");

    gestionUsuariosApiService.crearUsuario(usuarioDto);

  }

  private void validarDatosBasicos(UsuarioDto usuarioDto) {
    ValidationException validationException = new ValidationException("Errores de validación");
    boolean tieneErrores = false;

    if (usuarioDto.getNombre() == null || usuarioDto.getNombre().trim().isEmpty()) {
      validationException.addFieldError("nombre", "El nombre es obligatorio");
      tieneErrores = true;
    }

    if (usuarioDto.getCorreo() == null || usuarioDto.getCorreo().trim().isEmpty()) {
      validationException.addFieldError("correo", "El correo es obligatorio");
      tieneErrores = true;
    } else if (!usuarioDto.getCorreo().contains("@") || !usuarioDto.getCorreo().contains(".com")) {
      validationException.addFieldError("correo", "El correo ingresado es invalido");
      tieneErrores = true;
    }

    if (usuarioDto.getFechaNacimiento() == null || usuarioDto.getFechaNacimiento().toString().trim().isEmpty()) {
      validationException.addFieldError("fechaNacimiento", "La fecha de nacimineto es obligatoria");
      tieneErrores = true;
    }

    if (usuarioDto.getContrasenia() == null || usuarioDto.getContrasenia().trim().isEmpty()) {
      validationException.addFieldError("contrasenia", "La contrasenia es obligatoria");
      tieneErrores = true;
    }

    if(usuarioDto.getConfirmarContrasenia() == null || usuarioDto.getConfirmarContrasenia().trim().isEmpty()){
      validationException.addFieldError("confirmarContrasenia", "La confirmacion de contrasenia es obligatoria");
      tieneErrores = true;
    } else if (!usuarioDto.getConfirmarContrasenia().equals(usuarioDto.getContrasenia())){
      validationException.addFieldError("confirmarContrasenia", "Las contrasenias no coinciden");
      tieneErrores = true;
    }

    if (tieneErrores) {
      throw validationException;
    }

  }

  private void validarUsuarioExistente(UsuarioDto usuarioDto){
    if(gestionUsuariosApiService.existeUsuario(usuarioDto.getCorreo().trim())){
      throw new DuplicateCorreoException(usuarioDto.getCorreo().trim());
    }
  }
}
