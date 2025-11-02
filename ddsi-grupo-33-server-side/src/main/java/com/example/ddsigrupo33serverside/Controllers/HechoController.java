package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import com.example.ddsigrupo33serverside.Services.HechoApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechoController {

  private final HechoApiClient hechoApiClient;

  @GetMapping("/nuevo")
  public String mostrarFormularioDeHecho(Model model,
                                         @RequestParam(required = false) Boolean hechoCreado) {

    model.addAttribute("hecho", new HechoDto());

    if (hechoCreado != null && hechoCreado) {
      model.addAttribute("hechoCreado", true);
    }

    return "subir";
  }
  @PostMapping("/nuevo")
  public String crearHecho(@ModelAttribute("hecho") HechoDto hechoDto,
                           BindingResult result,
                           Model model) {
    if (result.hasErrors()) {
      return "subir"; // vuelve al formulario mostrando errores
    }
    hechoApiClient.crearHecho(hechoDto);
    return "redirect:/hechos/nuevo?hechoCreado=true";
  }

}
