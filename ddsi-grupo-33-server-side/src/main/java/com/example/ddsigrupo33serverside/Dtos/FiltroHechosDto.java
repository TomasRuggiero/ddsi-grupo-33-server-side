package com.example.ddsigrupo33serverside.Dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class FiltroHechosDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date fecha_desde;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date fecha_hasta;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date fecha_acontecimiento_desde;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date fecha_acontecimiento_hasta;
    public String latitud;
    public String longitud;
    public String titulo;
    public String categoria;
}