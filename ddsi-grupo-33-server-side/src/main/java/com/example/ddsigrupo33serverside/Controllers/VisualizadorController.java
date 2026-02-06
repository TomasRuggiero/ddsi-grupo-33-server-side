package com.example.ddsigrupo33serverside.Controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.example.ddsigrupo33serverside.Dtos.*;
import com.example.ddsigrupo33serverside.Services.ColeccionApiClient;
import com.example.ddsigrupo33serverside.Services.HechoApiClient;
import com.example.ddsigrupo33serverside.Services.SolicitudApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/visualizador")
@RequiredArgsConstructor
public class VisualizadorController {
  private final ColeccionApiClient coleccionService;
  private final HechoApiClient hechoService;
  private final SolicitudApiClient solicitudService;


  @GetMapping("mis-hechos")
  public String misHechos(Model model) {
    List<HechoDto> hechos = hechoService.getMisHechos();
    model.addAttribute("hechos", hechos);
    return "/visualizador/hechos-subidos";
  }

  @GetMapping("/colecciones")
  public String colecciones(Model model){
    List<ColeccionDto> colecciones = coleccionService.getTodasLasColecciones();
    model.addAttribute("colecciones", colecciones);
    return "/visualizador/colecciones";
  }

  @GetMapping("/colecciones/{id}")
  public String hechosPorColeccion(@PathVariable Long id, FiltroHechosDto filtros, @RequestParam(required = false) Boolean curado, Model model) {
    ColeccionDto coleccion = coleccionService.getColeccionPorId(id, filtros, curado);
    if (coleccion == null) {
      return "error/404"; // opcional
    }
    model.addAttribute("coleccion", coleccion);
    return "/visualizador/hechos-coleccion";
  }

  @GetMapping("/hecho/{id}")
  public String detalleHecho(@PathVariable UUID id, Model model) {
    HechoDto hecho = hechoService.getHechoPorId(id);
    if (hecho == null) {
      return "error/404"; // opcional
    }
    model.addAttribute("hecho", hecho);
    return "/visualizador/hecho";
  }

  @PostMapping("/hecho/editar/{id}")
  public String procesarEdicion(@PathVariable UUID id, @ModelAttribute HechoDto hechoDto,
                                @RequestParam(value = "categorias", required = false) String categoriasRaw) {

    if (categoriasRaw != null && !categoriasRaw.isBlank()) {
      Set<String> categoriasLimpias = Arrays.stream(categoriasRaw.split(","))
          .map(String::trim)
          .filter(c -> !c.isEmpty())
          .collect(Collectors.toSet());

      hechoDto.setCategorias(categoriasLimpias);
    } else {
      hechoDto.setCategorias(new HashSet<>());
    }

    hechoService.actualizarHecho(id, hechoDto);
    return "redirect:/visualizador/hechos-subidos?editado=true";
  }

  @PostMapping("/hecho/{id}/solicitar-eliminacion")
  public String solicitarEliminacion(@PathVariable UUID id,
                                     @RequestParam String justificacion) {
    SolicitudDto dto = new SolicitudDto();
    dto.setIdHecho(id);
    dto.setJustificacion(justificacion);
    solicitudService.crearSolicitud(dto);

    return "redirect:/visualizador/hecho/" + id + "?solicitudEnviada=true";
  }

}
