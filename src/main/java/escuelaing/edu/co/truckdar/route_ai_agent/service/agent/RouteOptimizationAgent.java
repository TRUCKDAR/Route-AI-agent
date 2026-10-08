package escuelaing.edu.co.truckdar.route_ai_agent.service.agent;

import escuelaing.edu.co.truckdar.route_ai_agent.dto.client.AlertSummaryDto;
import escuelaing.edu.co.truckdar.route_ai_agent.dto.client.RouteCompatibilityDto;
import escuelaing.edu.co.truckdar.route_ai_agent.dto.request.PlanRouteRequest;
import escuelaing.edu.co.truckdar.route_ai_agent.dto.response.AlternativeRouteResponse;
import escuelaing.edu.co.truckdar.route_ai_agent.dto.response.RoutePlanResponse;
import escuelaing.edu.co.truckdar.route_ai_agent.model.RouteRiskLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteOptimizationAgent {

    private final ColombianCorridorNetwork corridorNetwork;

    public RoutePlanResponse synthesizeOptimalRoute(
            PlanRouteRequest request,
            List<AlertSummaryDto> activeAlerts,
            RouteCompatibilityDto primaryCompatibility) {

        List<ColombianCorridorNetwork.CorridorOption> options = corridorNetwork.findOptions(
                request.getOriginCity(), request.getDestinationCity()
        );

        ColombianCorridorNetwork.CorridorOption chosenCorridor = options.get(0);
        List<String> warnings = new ArrayList<>();
        List<AlternativeRouteResponse> alternatives = new ArrayList<>();

        boolean primaryPassable = (primaryCompatibility != null && Boolean.TRUE.equals(primaryCompatibility.getIsPassable()));

        if (primaryCompatibility != null && primaryCompatibility.getWarnings() != null) {
            warnings.addAll(primaryCompatibility.getWarnings());
        }

        RouteRiskLevel riskLevel = RouteRiskLevel.BAJO;

        if (!primaryPassable) {
            riskLevel = RouteRiskLevel.INTRANSITABLE;
            warnings.add("BLOQUEO DE RUTA: " + (primaryCompatibility != null ? String.join(" | ", primaryCompatibility.getBlockingReasons()) : "Restricción activa"));

            if (options.size() > 1) {
                chosenCorridor = options.get(1);
                riskLevel = RouteRiskLevel.MEDIO;
                warnings.add("Agente AI aplicó desvío inteligente hacia corredor alternativo: " + chosenCorridor.getName());
            }
        }

        int alertsOnRoute = 0;
        for (AlertSummaryDto alert : activeAlerts) {
            alertsOnRoute++;
            if ("DERRUMBE".equalsIgnoreCase(alert.getAlertType()) || "CIERRE_TOTAL".equalsIgnoreCase(alert.getSeverity())) {
                riskLevel = RouteRiskLevel.ALTO;
                warnings.add("Alerta crítica en trayecto: " + alert.getTitle() + " (" + alert.getMunicipality() + ")");
            } else {
                warnings.add("Precaución vial: " + alert.getTitle());
            }
        }

        for (int i = 1; i < options.size(); i++) {
            ColombianCorridorNetwork.CorridorOption alt = options.get(i);
            alternatives.add(AlternativeRouteResponse.builder()
                    .corridorName(alt.getName())
                    .distanceKm(alt.getDistanceKm())
                    .estimatedHours(alt.getBaseHours())
                    .isPassable(true)
                    .riskLevel("MEDIO")
                    .issues(List.of("Ruta más extensa (+ " + Math.round(alt.getDistanceKm() - options.get(0).getDistanceKm()) + " km)"))
                    .build());
        }

        String aiRationale = generateAgentReasoning(chosenCorridor.getName(), riskLevel, alertsOnRoute, primaryPassable);

        return RoutePlanResponse.builder()
                .vehiclePlate(request.getVehiclePlate())
                .originCity(request.getOriginCity())
                .destinationCity(request.getDestinationCity())
                .recommendedCorridor(chosenCorridor.getName())
                .totalDistanceKm(chosenCorridor.getDistanceKm())
                .estimatedDurationHours(chosenCorridor.getBaseHours())
                .riskLevel(riskLevel)
                .activeAlertsCount(alertsOnRoute)
                .segments(chosenCorridor.getLegs())
                .warnings(warnings)
                .alternatives(alternatives)
                .aiRecommendation(aiRationale)
                .generatedAt(LocalDateTime.now())
                .build();
    }

    private String generateAgentReasoning(String corridor, RouteRiskLevel risk, int alerts, boolean isPassable) {
        if (!isPassable) {
            return String.format("El Agente de IA detectó restricciones normativas insuperables en la ruta principal. Se trazó un desvío por '%s' para salvaguardar la carga y garantizar viabilidad operativa.", corridor);
        }
        if (risk == RouteRiskLevel.ALTO) {
            return String.format("Ruta habilitada físicamente por '%s', pero con nivel de riesgo ALTO por presencia de %d incidente(s) climáticos o de orden público reportados por la comunidad.", corridor, alerts);
        }
        return String.format("Corredor '%s' seleccionado como óptimo. Parámetros de gálibo, peso bruto y condiciones viales verificados satisfactoriamente.", corridor);
    }
}