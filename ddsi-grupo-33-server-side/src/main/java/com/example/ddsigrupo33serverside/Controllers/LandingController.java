package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.ColeccionDto;
import com.example.ddsigrupo33serverside.Services.ColeccionApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LandingController {

  private final ColeccionApiClient coleccionApiClient;

  @GetMapping("/")
  public String landing(Model model) {

    List<ColeccionDto> colecciones = coleccionApiClient.getTodasLasColecciones();

    List<ColeccionDto> coleccionesHome = colecciones.stream()
        .limit(4)
        .toList();

    model.addAttribute("colecciones", coleccionesHome);

    return "index";
  }
}

