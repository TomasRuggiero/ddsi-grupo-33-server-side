package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.EstadisticaDto;
import com.example.ddsigrupo33serverside.Services.EstadisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/estadisticas")
public class EstadisticaController {

  private final EstadisticaService estadisticaService;

  @GetMapping()
  public String estadisticas(Model model) {

    EstadisticaDto estadisticaDto = estadisticaService.obtenerEstadistica();

    model.addAttribute("colecciones", estadisticaDto.getColeccionProvincia());
    model.addAttribute("categoriasProvincia", estadisticaDto.getCategoriaProvincia());
    model.addAttribute("categoriasHora", estadisticaDto.getCategoriaHora());
    model.addAttribute("categoriaMasHechos", estadisticaDto.getCategoriaConMasHechos());
    model.addAttribute("solicitudesSpam", estadisticaDto.getSolicitudesSpam());
    model.addAttribute("titulo", "Administración");
    model.addAttribute("menu", "admin");

    return "estadisticas";
  }

}
