package escuelaing.edu.co.truckdar.route_ai_agent.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteCompatibilityDto {
    private String vehiclePlate;
    private String roadCorridor;
    private Boolean isPassable;
    private List<String> blockingReasons;
    private List<String> warnings;
}