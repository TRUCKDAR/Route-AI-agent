package escuelaing.edu.co.truckdar.route_ai_agent.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertSummaryDto {
    private Long id;
    private String alertType;
    private String severity;
    private String title;
    private String description;
    private String department;
    private String municipality;
    private Double latitude;
    private Double longitude;
}