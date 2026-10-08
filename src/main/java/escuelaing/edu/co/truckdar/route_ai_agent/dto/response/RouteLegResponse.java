package escuelaing.edu.co.truckdar.route_ai_agent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteLegResponse {
    private String segmentName;
    private Double distanceKm;
    private String condition;
    private String checkpointName;
}