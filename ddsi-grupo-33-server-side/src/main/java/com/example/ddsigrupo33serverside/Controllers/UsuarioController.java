package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.UsuarioDto;
import com.example.ddsigrupo33serverside.Exceptions.DuplicateCorreoException;
import com.example.ddsigrupo33serverside.Exceptions.ValidationException;
import com.example.ddsigrupo33serverside.Services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class UsuarioController {

  private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
  private final UsuarioService usuarioService;

  @GetMapping
  public String registrar(Model model){
    model.addAttribute("usuario", new UsuarioDto());
    model.addAttribute("titulo", "Registro de usuario");
    return "register";
  }

  @PostMapping
  public String crearUsuario(@ModelAttribute("usuario")UsuarioDto usuarioDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes){
    try {
      usuarioService.crearUsuario(usuarioDto);
      redirectAttributes.addFlashAttribute("mensaje", "Usuario registrado exitosamente");
      redirectAttributes.addFlashAttribute("tipoMensaje", "success");
      return "redirect:/login";
    }
    catch (DuplicateCorreoException e) {
      log.error("El correo ya se encuentra en uso", e);
      //model.addAttribute("error", "El correo indicado ya se encuentra en uso");
      model.addAttribute("titulo", "Crear Nuevo Usuario");
      redirectAttributes.addFlashAttribute("error", "El correo indicado ya se encuentra en uso");
      log.error(e.getMessage());
      e.printStackTrace();
      return "redirect:/register?error=true";
    }
    catch (ValidationException e) {
      convertirValidationExceptionABindingResult(e, bindingResult);
      model.addAttribute("titulo", "Crear Nuevo Alumno");
      log.error(e.getMessage());
      e.printStackTrace();
      return "register";
    }
    catch (Exception e) {
      log.error("Error al crear usuario", e);
      //model.addAttribute("error", "Error al crear el usuario: " + e.getMessage());
      redirectAttributes.addFlashAttribute("error", "Error al crear el usuario. Intentelo nuevamente en un rato");
      model.addAttribute("titulo", "Crear Nuevo Usuario");
      log.error(e.getMessage());
      e.printStackTrace();
      return "redirect:/register?error=true";
    }
  }

  private void convertirValidationExceptionABindingResult(ValidationException e, BindingResult bindingResult) {
    if(e.hasFieldErrors()) {
      e.getFieldErrors().forEach((field, error) -> bindingResult.rejectValue(field, "error." + field, error));
    }
  }

}