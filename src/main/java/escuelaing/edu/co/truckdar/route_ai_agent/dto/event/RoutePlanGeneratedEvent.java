package escuelaing.edu.co.truckdar.route_ai_agent.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutePlanGeneratedEvent {
    private Long planId;
    private String vehiclePlate;
    private String originCity;
    private String destinationCity;
    private String chosenCorridor;
    private Double totalDistanceKm;
    private Double durationHours;
    private String riskLevel;
    private LocalDateTime timestamp;
}