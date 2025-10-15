package com.example.ddsigrupo33serverside.Services;

import com.example.ddsigrupo33serverside.Dtos.AdminColeccionDto;
import com.example.ddsigrupo33serverside.Dtos.AdminHomeDto;
import com.example.ddsigrupo33serverside.Dtos.HechoDto;
import com.example.ddsigrupo33serverside.Dtos.SolicitudDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {
  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://localhost:8080";

    public AdminHomeDto getAdminHome() {
        List<HechoDto> todosLosHechos = List.of(
            new HechoDto(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "Incendio en Córdoba",
                "2024-12-03",
                "Córdoba, Argentina",
                "Estática",
                Set.of("Incendio", "Medioambiente"),
                "-64.1833",  // longitud
                "-31.4167"   // latitud
            ),
            new HechoDto(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                "Reforestación en La Cumbre",
                "2025-01-20",
                "La Cumbre, Córdoba",
                "Dinámica",
                Set.of("Reforestación", "Voluntariado"),
                "-64.1833",  // longitud
                "-31.4167"   // latitud
            )
        );


        List<HechoDto> hechosUltimaSemana = new ArrayList<>(todosLosHechos.subList(1, 2));

        return new AdminHomeDto(todosLosHechos, hechosUltimaSemana, todosLosHechos.size(), 10, 100, 10000);
    }

    public List<AdminColeccionDto> getAdminColecciones() {
        List<AdminColeccionDto> adminColecciones = new ArrayList<>();

        // 1. Ejemplo original
        adminColecciones.add(new AdminColeccionDto(1, "Eventos Históricos", "Colección de los hechos más relevantes de la historia mundial.", "Mayoria_Simple", 100, new Date()));

        // Creamos algunas fechas de ejemplo para variedad
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        Date haceUnMes = cal.getTime();
        cal.add(Calendar.MONTH, -6);
        Date haceSeisMeses = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        Date haceUnAnio = cal.getTime();

        // 2. Guerra y Paz (Ejemplo con fecha antigua)
        adminColecciones.add(new AdminColeccionDto(2, "Conflictos Bélicos", "Recopilación de las grandes guerras y tratados de paz.", "Consenso", 85, haceUnAnio));

        // 3. Fauna Argentina
        adminColecciones.add(new AdminColeccionDto(3, "Especies Nativas", "Hechos sobre la fauna endémica de la región pampeana y patagónica.", "Mayoria_Simple", 210, haceUnMes));

        // 4. Arquitectura Moderna
        adminColecciones.add(new AdminColeccionDto(4, "Obras del Siglo XX", "Hechos relevantes de la arquitectura moderna y sus creadores.", "Consenso", 45, new Date()));

        // 5. Astronomía
        adminColecciones.add(new AdminColeccionDto(5, "Eventos Cósmicos", "Colección de datos sobre supernovas, galaxias y planetas.", "Mayoria_Simple", 320, haceSeisMeses));

        // 6. Tecnología y Futuro
        adminColecciones.add(new AdminColeccionDto(6, "Innovaciones Clave", "Impacto de la inteligencia artificial y el desarrollo de software.", "Consenso", 60, new Date()));

        // 7. Política Internacional
        adminColecciones.add(new AdminColeccionDto(7, "Relaciones de Poder", "Hechos sobre acuerdos bilaterales y organizaciones mundiales.", "Mayoria_Simple", 150, new Date()));

        // 8. Ciencia y Descubrimientos
        adminColecciones.add(new AdminColeccionDto(8, "Premios Nobel", "Hechos relacionados con los principales avances científicos.", "Consenso", 92, haceUnMes));

        // 9. Gastronomía Regional
        adminColecciones.add(new AdminColeccionDto(9, "Platos Típicos", "Hechos sobre la historia y elaboración de la comida local.", "Mayoria_Simple", 35, new Date()));

        // 10. Música Clásica
        adminColecciones.add(new AdminColeccionDto(10, "Compositores y Obras", "Colección dedicada a las sinfonías y óperas más influyentes.", "Consenso", 180, haceSeisMeses));

        return adminColecciones;
    }

    public List<SolicitudDto> getSolicitudes() {
      SolicitudDto[] solicitudes = restTemplate.getForObject(BASE_URL + "/solicitudes", SolicitudDto[].class);
      return Arrays.asList(solicitudes);
    }
}
