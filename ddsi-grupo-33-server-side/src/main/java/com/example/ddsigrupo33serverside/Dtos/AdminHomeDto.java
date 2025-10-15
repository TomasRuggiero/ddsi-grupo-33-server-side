package com.example.ddsigrupo33serverside.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminHomeDto {
    public List<HechoDto> hechosPorCategoria;
    public List<HechoDto> hechosUltimaSemana;

    public Integer totalHechos;
    public Integer totalColecciones;
    public Integer totalSolicitudesDeEliminacion;
    public Integer totalUsers;
}
