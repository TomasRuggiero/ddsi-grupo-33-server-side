package com.example.ddsigrupo33serverside.Dtos;

import lombok.Data;

@Data
public class AdminUsersDto {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
}
