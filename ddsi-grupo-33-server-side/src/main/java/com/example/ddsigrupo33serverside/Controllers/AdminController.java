package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.AdminHomeDto;
import com.example.ddsigrupo33serverside.Dtos.ColeccionInputDto;
import com.example.ddsigrupo33serverside.Dtos.FuenteProxyInputDto;
import com.example.ddsigrupo33serverside.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
  @PreAuthorize("hasAnyRole('ADMIN')")
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
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<?> crearColeccion(
          @RequestBody ColeccionInputDto dto,
          @RequestParam(required = false) String algoritmoDeConsenso) {

    coleccionApiClient.crearColeccion(dto, algoritmoDeConsenso);

    return ResponseEntity.ok().build();
  }

    @PutMapping("/colecciones/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> actualizar(
        @PathVariable Long id,
        @RequestBody ColeccionInputDto dto,
        @RequestParam(required = false) String algoritmoDeConsenso) {

      coleccionApiClient.actualizarColeccion(dto, id, algoritmoDeConsenso);

      return ResponseEntity.ok().build();
    }

    @DeleteMapping("/colecciones/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
      coleccionApiClient.eliminarColeccion(id);

      return ResponseEntity.ok().build();
    }

    @PostMapping("/revisar")
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String adminHome(Model model) {
        AdminHomeDto dto = adminService.getAdminHome();
        model.addAttribute("todosLosHechos", dto.getHechosPorCategoria());
        model.addAttribute("hechosUltimaSemana", dto.getHechosUltimaSemana());
        model.addAttribute("totalHechos", dto.getTotalHechos());
        model.addAttribute("totalSolicitudesDeEliminacion", dto.getTotalSolicitudesDeEliminacion());
        model.addAttribute("totalUsuarios", dto.getTotalUsers());
        model.addAttribute("totalColecciones", dto.getTotalColecciones());
        model.addAttribute("titulo", "Administración");
        model.addAttribute("menu", "admin");
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = inicioSemana.plusDays(6);

        model.addAttribute("rangoSemana", inicioSemana.getDayOfMonth() + "/" + inicioSemana.getMonthValue() +
                " al " + finSemana.getDayOfMonth() + "/" + finSemana.getMonthValue());

        return "administrador/index";
    }

    @GetMapping("/colecciones")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String colecciones(Model model) {
        model.addAttribute("colecciones", adminService.getAdminColecciones());
        model.addAttribute("todasLasFuentes", adminService.getAllFuentes());
        model.addAttribute("titulo", "Colecciones");
        model.addAttribute("menu", "colecciones");

        return "administrador/colecciones";
    }

    @GetMapping("/solicitudes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String solicitudes(Model model) {
      model.addAttribute("solicitudes", adminService.getSolicitudes());
      model.addAttribute("titulo", "Solicitudes");
      model.addAttribute("menu", "solicitudes");

      return "administrador/solicitudes";
    }

    @GetMapping("hechos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String hechos(Model model) {
      model.addAttribute("hechos", adminService.getAdminHechos());
      model.addAttribute("titulo", "Hechos");
      model.addAttribute("menu", "hechos");

      return "administrador/hechos";
    }

    @GetMapping("fuentes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String fuentes(Model model) {
      model.addAttribute("fuentes", adminService.getAllFuentes());
      model.addAttribute("titulo", "Fuentes");
      model.addAttribute("menu", "fuentes");

      return "administrador/fuentes";
    }

    @DeleteMapping("/fuentes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminarFuente(@PathVariable Long id) {
      fuenteApiClient.eliminar(id);

      return ResponseEntity.noContent().build();
    }

    @PostMapping("/solicitudes/{id}/aceptar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String aceptarSolicitud(@PathVariable Integer id) {
      solicitudApiClient.aceptar(id);

      return "redirect:/admin/solicitudes";
    }

    @PostMapping("/solicitudes/{id}/rechazar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String rechazarSolicitud(@PathVariable Integer id) {
      solicitudApiClient.rechazar(id);

      return "redirect:/admin/solicitudes";
    }

    @GetMapping("usuarios")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String usuarios(Model model,
                           @RequestParam(required = false) String nombre,
                           @RequestParam(required = false) String correo,
                           @RequestParam(required = false) String rol) {

      model.addAttribute("usuariosFiltrados", usuarioApiClient.listarUsuarios(nombre, correo, rol));
      model.addAttribute("titulo", "Gestión de Usuarios");
      model.addAttribute("menu", "usuarios");

      return "administrador/usuarios";
    }

    @PutMapping("usuarios/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> promoverUsuario(
        @PathVariable Long id
    ) {
      usuarioApiClient.promoverUsuario(id);

      return ResponseEntity.ok().build();
    }

    @DeleteMapping("usuarios/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminarUsuario(
        @PathVariable Long id
    ) {
      usuarioApiClient.borrarUsuario(id);

      return ResponseEntity.noContent().build();
    }
}
