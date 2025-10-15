package com.example.ddsigrupo33serverside.Dtos;

import com.example.ddsigrupo33serverside.Enums.Permiso;
import com.example.ddsigrupo33serverside.Enums.Rol;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
  private String accessToken;
  private String refreshToken;
  private String username;
  private Rol rol;
  private List<Permiso> permisos;
}
