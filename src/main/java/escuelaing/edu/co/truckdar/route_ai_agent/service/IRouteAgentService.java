package escuelaing.edu.co.truckdar.route_ai_agent.service;

import escuelaing.edu.co.truckdar.route_ai_agent.dto.request.PlanRouteRequest;
import escuelaing.edu.co.truckdar.route_ai_agent.dto.response.RoutePlanResponse;

import java.util.List;

public interface IRouteAgentService {
    RoutePlanResponse planOptimalRoute(PlanRouteRequest request);
    List<RoutePlanResponse> getHistoryByPlate(String vehiclePlate);
}