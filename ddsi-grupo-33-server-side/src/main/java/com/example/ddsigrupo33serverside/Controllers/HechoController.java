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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechoController {
  private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
  private static final int MAX_FILES = 5;

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
                           @RequestParam(value = "archivos",required = false) List<MultipartFile> archivos,
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
            "Uno o más archivos superan el tamaño máximo permitido (10MB)"
        );
        return "subir";
      }
    }

    if (archivos != null) {
      long cantidadArchivos = archivos.stream()
          .filter(a -> !a.isEmpty())
          .count();

      if (cantidadArchivos > MAX_FILES) {
        model.addAttribute("hecho", hechoDto);
        model.addAttribute(
            "error",
            "No se pueden subir más de 5 archivos. El máximo permitido es 5."
        );
        return "subir";
      }
    }

    UUID idHecho = hechoApiClient.crearHecho(hechoDto);

    if (idHecho != null && archivos != null) {

      List<MultipartFile> archivosValidos = archivos.stream()
          .filter(a -> !a.isEmpty())
          .toList();

      try {
        if (!archivosValidos.isEmpty()) {
          hechoApiClient.agregarMultimedia(idHecho, archivosValidos);
        }
      } catch (HttpClientErrorException e) {

        model.addAttribute("hecho", hechoDto);
        model.addAttribute(
            "error",
            e.getResponseBodyAsString() != null
                ? e.getResponseBodyAsString()
                : "Error al subir archivos"
        );

        return "subir";
      }
    }

    // 4. Redirigir
    return "redirect:/hechos/nuevo?hechoCreado=true";
  }

}
