package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.ColeccionInputDto;
import com.example.ddsigrupo33serverside.Dtos.FuenteProxyInputDto;
import com.example.ddsigrupo33serverside.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    private final SolicitudApiClient solicitudApiClient;
    private final HechoApiClient hechoApiClient;
    private final ColeccionApiClient coleccionApiClient;
    private final FuenteApiClient fuenteApiClient;
    private final UsuarioApiClient usuarioApiClient;

  @PostMapping("/fuentes/{tipo}")
  public ResponseEntity<Long> crearFuente(
      @PathVariable String tipo,
      @RequestParam(value = "archivo", required = false) MultipartFile archivo,
      @RequestParam(value = "username", required = false) String username,
      @RequestParam(value = "password", required = false) String password
  ) {
    if ("estatica".equals(tipo) && archivo != null) {
      fuenteApiClient.crearFuenteEstatica(archivo);
      return ResponseEntity.ok().build();
    }

    if ("proxy".equals(tipo) && username != null && password != null) {
      FuenteProxyInputDto dto = new FuenteProxyInputDto();
      dto.setUsername(username);
      dto.setPassword(password);
      fuenteApiClient.crearFuenteProxy(dto);
      return ResponseEntity.ok().build();
    }

    if ("dinamica".equals(tipo)) {
      fuenteApiClient.crearFuenteDinamica();
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.badRequest().build();
  }

    @PostMapping("/colecciones")
    public ResponseEntity<?> crearColeccion(
        @RequestBody ColeccionInputDto dto) {

      coleccionApiClient.crearColeccion(dto);

      return ResponseEntity.ok().build();
    }

    @PutMapping("/colecciones/{id}")
    public ResponseEntity<?> actualizar(
        @PathVariable Long id,
        @RequestBody ColeccionInputDto dto) {

      coleccionApiClient.actualizarColeccion(dto, id);

      return ResponseEntity.ok().build();
    }

    @DeleteMapping("/colecciones/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
      coleccionApiClient.eliminarColeccion(id);

      return ResponseEntity.ok().build();
    }

    @PostMapping("/revisar")
    public String procesarRevision(
            @RequestParam("id") UUID id,
            @RequestParam("accion") String accion,
            @RequestParam(value = "sugerencia", required = false) String sugerencia
    ) {
        if ("ACEPTAR".equals(accion)) {
            hechoApiClient.aceptar(id, sugerencia);
        } else if ("RECHAZAR".equals(accion)) {
            hechoApiClient.rechazar(id, sugerencia);
        }

        return "redirect:/admin/hechos";
    }

    @GetMapping()
    public String adminHome(Model model) {
        model.addAttribute("todosLosHechos", adminService.getAdminHome().getHechosPorCategoria());
        model.addAttribute("hechosUltimaSemana", adminService.getAdminHome().getHechosUltimaSemana());
        model.addAttribute("totalHechos", adminService.getAdminHome().getTotalHechos());
        model.addAttribute("totalSolicitudesDeEliminacion", adminService.getAdminHome().getTotalSolicitudesDeEliminacion());
        model.addAttribute("totalUsuarios", adminService.getAdminHome().getTotalUsers());
        model.addAttribute("totalColecciones", adminService.getAdminHome().getTotalColecciones());

        return "administrador/index";
    }

    @GetMapping("/colecciones")
    public String colecciones(Model model) {
        model.addAttribute("colecciones", adminService.getAdminColecciones());
        model.addAttribute("todasLasFuentes", adminService.getAllFuentes());

        return "administrador/colecciones";
    }

    @GetMapping("/solicitudes")
    public String solicitudes(Model model) {
      model.addAttribute("solicitudes", adminService.getSolicitudes());

      return "administrador/solicitudes";
    }

    @GetMapping("hechos")
    public String hechos(Model model) {
      model.addAttribute("hechos", adminService.getAdminHechos());

      return "administrador/hechos";
    }

    @GetMapping("fuentes")
    public String fuentes(Model model) {
      model.addAttribute("fuentes", adminService.getAllFuentes());

      return "administrador/fuentes";
    }

    @DeleteMapping("/fuentes/{id}")
    public ResponseEntity<?> eliminarFuente(@PathVariable Long id) {
      fuenteApiClient.eliminar(id);

      return ResponseEntity.noContent().build();
    }

    @PostMapping("/solicitudes/{id}/aceptar")
    public String aceptarSolicitud(@PathVariable Integer id) {
      solicitudApiClient.aceptar(id);

      return "redirect:/admin/solicitudes";
    }

    @PostMapping("/solicitudes/{id}/rechazar")
    public String rechazarSolicitud(@PathVariable Integer id) {
      solicitudApiClient.rechazar(id);

      return "redirect:/admin/solicitudes";
    }

    @GetMapping("usuarios")
    public String usuarios(Model model,
                           @RequestParam(required = false) String nombre,
                           @RequestParam(required = false) String correo,
                           @RequestParam(required = false) String rol) {

      model.addAttribute("usuariosFiltrados", usuarioApiClient.listarUsuarios(nombre, correo, rol));

      return "administrador/usuarios";
    }
}
