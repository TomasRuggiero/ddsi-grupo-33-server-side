package com.example.ddsigrupo33serverside.Controllers;

import com.example.ddsigrupo33serverside.Services.AdminService;
import com.example.ddsigrupo33serverside.Services.SolicitudApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    private final SolicitudApiClient solicitudService;

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

        return "administrador/colecciones";
    }

    @GetMapping("/solicitudes")
    public String solicitudes(Model model) {
      model.addAttribute("solicitudes", adminService.getSolicitudes());

      return "administrador/solicitudes";
    }

    @PostMapping("/solicitudes/{id}/aceptar")
    public String aceptarSolicitud(@PathVariable Integer id) {
      solicitudService.aceptar(id);

      return "redirect:/admin/solicitudes";
    }

    @PostMapping("/solicitudes/{id}/rechazar")
    public String rechazarSolicitud(@PathVariable Integer id) {
      solicitudService.rechazar(id);

      return "redirect:/admin/solicitudes";
    }
}
