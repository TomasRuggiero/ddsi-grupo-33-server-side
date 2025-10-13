package com.example.ddsigrupo33serverside.Controllers;

import java.util.List;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Services.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
