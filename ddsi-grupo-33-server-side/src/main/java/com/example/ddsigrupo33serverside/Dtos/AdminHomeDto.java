package com.example.ddsigrupo33serverside.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminHomeDto {
    private List<HechoDto> hechosPorCategoria;
    private List<HechoDto> hechosUltimaSemana;

    private Long totalHechos;
    private Integer totalColecciones;
    private Integer totalSolicitudesDeEliminacion;
    private Integer totalUsers;
}
