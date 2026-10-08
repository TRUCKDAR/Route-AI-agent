package escuelaing.edu.co.truckdar.route_ai_agent.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanRouteRequest {

    @NotBlank(message = "La placa del camión es obligatoria")
    private String vehiclePlate;

    @NotBlank(message = "El tipo de camión es obligatorio (ej. TRACTOMULA_3S3)")
    private String truckType;

    @NotNull(message = "El peso bruto total en toneladas es obligatorio")
    private Double grossWeightTons;

    @NotNull(message = "La altura del camión en metros es obligatoria")
    private Double heightMeters;

    @NotBlank(message = "La ciudad de origen es obligatoria")
    private String originCity;

    @NotBlank(message = "La ciudad de destino es obligatoria")
    private String destinationCity;

    private Double originLatitude;
    private Double originLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
}