package escuelaing.edu.co.truckdar.route_ai_agent.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route_decision_logs", indexes = {
        @Index(name = "idx_decision_vehicle", columnList = "vehicle_plate"),
        @Index(name = "idx_decision_corridor", columnList = "selected_corridor")
})
public class RouteDecisionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_plate", nullable = false)
    private String vehiclePlate;

    @Column(name = "origin_city", nullable = false)
    private String originCity;

    @Column(name = "destination_city", nullable = false)
    private String destinationCity;

    @Column(name = "selected_corridor", nullable = false)
    private String selectedCorridor;

    @Column(name = "total_distance_km")
    private Double totalDistanceKm;

    @Column(name = "estimated_duration_hours")
    private Double estimatedDurationHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false)
    private RouteRiskLevel riskLevel;

    @Column(name = "active_alerts_count")
    private Integer activeAlertsCount;

    @Column(name = "restrictions_evaluated_count")
    private Integer restrictionsEvaluatedCount;

    @Column(name = "agent_recommendation", length = 1500)
    private String agentRecommendation;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}