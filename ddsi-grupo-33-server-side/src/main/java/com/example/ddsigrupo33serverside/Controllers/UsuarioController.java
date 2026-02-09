package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.UsuarioDto;
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
    return "/register";
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
    //luego habría que agregar para casos especiales un redirect a /register y que bindee el usuario
    catch (Exception e) {
      log.error("Error al crear usuario", e);
      model.addAttribute("error", "Error al crear el usuario: " + e.getMessage());
      model.addAttribute("titulo", "Crear Nuevo Usuario");
      return "redirect:/register?error=true";
    }
  }

}


/*
    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN') and hasAnyAuthority('CREAR_ALUMNOS')")
    public String crearAlumno(@ModelAttribute("alumno")AlumnoDTO alumnoDTO,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            AlumnoDTO alumnoCreado = alumnoService.crearAlumno(alumnoDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Alumno creado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/alumnos/" + alumnoCreado.getLegajo();
        }
        catch (DuplicateLegajoException ex) {
            bindingResult.rejectValue("legajo", "error.legajo", ex.getMessage());
            model.addAttribute("titulo", "Crear Nuevo Alumno");
            return "alumnos/crear";
        }
        catch (ValidationException e) {
            convertirValidationExceptionABindingResult(e, bindingResult);
            model.addAttribute("titulo", "Crear Nuevo Alumno");
            return "alumnos/crear";
        }
        catch (Exception e) {
            log.error("Error al crear alumno", e);
            model.addAttribute("error", "Error al crear el alumno: " + e.getMessage());
            model.addAttribute("titulo", "Crear Nuevo Alumno");
            return "alumnos/crear";
        }
    }
 */