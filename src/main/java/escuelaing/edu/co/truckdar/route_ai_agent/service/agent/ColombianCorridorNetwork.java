package escuelaing.edu.co.truckdar.route_ai_agent.service.agent;

import escuelaing.edu.co.truckdar.route_ai_agent.dto.response.RouteLegResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Component
public class ColombianCorridorNetwork {

    public static class CorridorOption {
        private final String name;
        private final Double distanceKm;
        private final Double baseHours;
        private final List<RouteLegResponse> legs;

        public CorridorOption(String name, Double distanceKm, Double baseHours, List<RouteLegResponse> legs) {
            this.name = name;
            this.distanceKm = distanceKm;
            this.baseHours = baseHours;
            this.legs = legs;
        }

        public String getName() { return name; }
        public Double getDistanceKm() { return distanceKm; }
        public Double getBaseHours() { return baseHours; }
        public List<RouteLegResponse> getLegs() { return legs; }
    }

    private final Map<String, List<CorridorOption>> network = Map.of(
            "BOGOTA-BUENAVENTURA", List.of(
                    new CorridorOption("Bogotá - Ibagué - Armenia - Buga - Buenaventura (Vía La Línea)", 514.0, 11.5, List.of(
                            new RouteLegResponse("Bogotá -> Ibagué", 200.0, "Doble calzada óptima", "Peaje Chusacá"),
                            new RouteLegResponse("Ibagué -> Cajamarca -> Túnel de La Línea -> Calarcá", 72.0, "Tramo montañoso de alta pendiente", "Túnel de La Línea"),
                            new RouteLegResponse("Calarcá -> Buga -> Buenaventura", 242.0, "Corredor logístico Pacífico", "Peaje Loboguerrero")
                    )),
                    new CorridorOption("Bogotá - Manizales - Pereira - Buga - Buenaventura (Vía Letras)", 580.0, 14.0, List.of(
                            new RouteLegResponse("Bogotá -> Honda -> Alto de Letras", 240.0, "Ascenso cordillera central", "Páramo de Letras"),
                            new RouteLegResponse("Manizales -> Pereira -> Cartago", 110.0, "Eje cafetero fluido", "Peaje Cerritos"),
                            new RouteLegResponse("Cartago -> Buga -> Buenaventura", 230.0, "Descenso hacia terminal marítimo", "Peaje Loboguerrero")
                    ))
            ),
            "BOGOTA-MEDELLIN", List.of(
                    new CorridorOption("Bogotá - Villeta - Guaduas - Honda - Medellín (Autopista Medellín-Bogotá)", 415.0, 9.5, List.of(
                            new RouteLegResponse("Bogotá -> Villeta -> Guaduas", 115.0, "Descenso sabana de Bogotá", "Peaje Siberia"),
                            new RouteLegResponse("Guaduas -> Doradal -> Santuario", 210.0, "Valle del Magdalena Medio", "Peaje Puerto Triunfo"),
                            new RouteLegResponse("Santuario -> Rionegro -> Medellín", 90.0, "Ascenso oriente antioqueño", "Peaje Copacabana")
                    ))
            )
    );

    public List<CorridorOption> findOptions(String origin, String destination) {
        String key = (normalizeCity(origin) + "-" + normalizeCity(destination)).toUpperCase();
        return network.getOrDefault(key, List.of(
                new CorridorOption(origin + " - " + destination + " (Troncal Principal)", 450.0, 10.0, List.of(
                        new RouteLegResponse(origin + " -> Checkpoint Intermedio", 225.0, "Vía nacional regular", "Control Vial Central"),
                        new RouteLegResponse("Checkpoint Intermedio -> " + destination, 225.0, "Aproximación urbana", "Terminal de Carga")
                ))
        ));
    }

    private String normalizeCity(String city) {
        if (city == null) return "";
        return city.trim().toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replaceAll("(?i) D\\.C\\.", "")
                .replaceAll("(?i) DISTRITO CAPITAL", "")
                .trim();
    }
}