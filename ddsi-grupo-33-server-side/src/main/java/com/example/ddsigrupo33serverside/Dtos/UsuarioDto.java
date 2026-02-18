package com.example.ddsigrupo33serverside.Dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
  private String nombre;
  private LocalDate fechaNacimiento;
  private String correo;
  private String contrasenia;
  private String confirmarContrasenia;
}
