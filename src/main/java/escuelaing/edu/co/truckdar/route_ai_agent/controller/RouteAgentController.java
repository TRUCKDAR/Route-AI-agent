package escuelaing.edu.co.truckdar.route_ai_agent.controller;

import escuelaing.edu.co.truckdar.route_ai_agent.dto.request.PlanRouteRequest;
import escuelaing.edu.co.truckdar.route_ai_agent.dto.response.RoutePlanResponse;
import escuelaing.edu.co.truckdar.route_ai_agent.service.IRouteAgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RouteAgentController {

    private final IRouteAgentService service;

    @PostMapping("/plan")
    public ResponseEntity<RoutePlanResponse> planRoute(@Valid @RequestBody PlanRouteRequest request) {
        return ResponseEntity.ok(service.planOptimalRoute(request));
    }

    @GetMapping("/history/{plate}")
    public ResponseEntity<List<RoutePlanResponse>> getHistoryByPlate(@PathVariable String plate) {
        return ResponseEntity.ok(service.getHistoryByPlate(plate));
    }
}