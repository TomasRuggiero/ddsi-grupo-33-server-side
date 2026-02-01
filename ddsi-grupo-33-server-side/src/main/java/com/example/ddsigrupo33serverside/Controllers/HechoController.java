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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechoController {
  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
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
                           @RequestParam("archivos") List<MultipartFile> archivos,
                           Model model) throws IOException {

    if (result.hasErrors()) {
      model.addAttribute("hecho", hechoDto);
      model.addAttribute("hechoRechazado", true);
      return "subir";
    }

    if (archivos != null) {
      boolean hayArchivoGrande = archivos.stream()
          .filter(a -> !a.isEmpty())
          .anyMatch(a -> a.getSize() > MAX_FILE_SIZE);

      if (hayArchivoGrande) {
        model.addAttribute("hecho", hechoDto);
        model.addAttribute(
            "error",
            "Uno o más archivos superan el tamaño máximo permitido (5MB)"
        );
        return "subir";
      }
    }

    UUID idHecho = hechoApiClient.crearHecho(hechoDto);

    if (idHecho != null && archivos != null) {

      List<MultipartFile> archivosValidos = archivos.stream()
          .filter(a -> !a.isEmpty())
          .toList();

      if (!archivosValidos.isEmpty()) {
        hechoApiClient.agregarMultimedia(idHecho, archivosValidos);
      }
    }

    // 4. Redirigir
    return "redirect:/hechos/nuevo?hechoCreado=true";
  }

}
