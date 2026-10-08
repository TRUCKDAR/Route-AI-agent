package escuelaing.edu.co.truckdar.route_ai_agent.dto.response;

import escuelaing.edu.co.truckdar.route_ai_agent.model.RouteRiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutePlanResponse {
    private Long planId;
    private String vehiclePlate;
    private String originCity;
    private String destinationCity;
    private String recommendedCorridor;
    private Double totalDistanceKm;
    private Double estimatedDurationHours;
    private RouteRiskLevel riskLevel;
    private Integer activeAlertsCount;
    private List<RouteLegResponse> segments;
    private List<String> warnings;
    private List<AlternativeRouteResponse> alternatives;
    private String aiRecommendation;
    private LocalDateTime generatedAt;
}
