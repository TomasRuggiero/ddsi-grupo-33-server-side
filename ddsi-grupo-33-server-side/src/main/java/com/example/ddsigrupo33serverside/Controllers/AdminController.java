package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Dtos.ColeccionInputDto;
import com.example.ddsigrupo33serverside.Services.AdminService;
import com.example.ddsigrupo33serverside.Services.ColeccionApiClient;
import com.example.ddsigrupo33serverside.Services.HechoApiClient;
import com.example.ddsigrupo33serverside.Services.SolicitudApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    private final SolicitudApiClient solicitudApiClient;
    private final HechoApiClient hechoApiClient;
    private final ColeccionApiClient coleccionApiClient;

    @PutMapping("/colecciones/{id}")
    public String actualizar(
        @PathVariable Long id,
        @RequestBody ColeccionInputDto dto) {

      System.out.println("Titulo recibido: " + dto.getTitulo());
      System.out.println(dto.getIdsFuentesDeDatos());
      System.out.println(dto.getCriterioDePertenencia().getLatitud());

      coleccionApiClient.actualizarColeccion(dto);

      return "redirect:/admin/colecciones/";
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
}
