package com.example.ddsigrupo33serverside.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AdminColeccionDto {
    public Integer id;
    public String nombre;
    public String descripcion;
    public String AlgoritmoDeConsenso;
    public Integer totalHechos;
    public Date fecha;
}
