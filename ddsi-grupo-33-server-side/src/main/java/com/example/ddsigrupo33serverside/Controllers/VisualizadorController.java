package com.example.ddsigrupo33serverside.Controllers;

import java.util.List;
import java.util.UUID;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import com.example.ddsigrupo33serverside.Services.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visualizador")
@RequiredArgsConstructor
public class VisualizadorController {
  private final ColeccionService coleccionService;

  @GetMapping("/colecciones")
  public String colecciones(Model model){
    List<ColeccionDto> colecciones = coleccionService.getTodasLasColecciones();
    model.addAttribute("colecciones", colecciones);
    return "/visualizador/colecciones";
  }

  @GetMapping("/colecciones/{id}")
  public String hechosPorColeccion(@PathVariable Long id, Model model) {
    ColeccionDto coleccion = coleccionService.getColeccionPorId(id);
    if (coleccion == null) {
      return "error/404"; // opcional
    }
    model.addAttribute("coleccion", coleccion);
    return "/visualizador/hechos-coleccion";
  }

  @GetMapping("/hecho/{id}")
  public String detalleHecho(@PathVariable UUID id, Model model) {
    HechoDto hecho = coleccionService.getHechoPorId(id);
    if (hecho == null) {
      return "error/404"; // opcional
    }
    model.addAttribute("hecho", hecho);
    return "/visualizador/hecho";
  }

}
