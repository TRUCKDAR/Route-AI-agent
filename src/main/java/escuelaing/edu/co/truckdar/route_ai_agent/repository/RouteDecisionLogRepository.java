package escuelaing.edu.co.truckdar.route_ai_agent.repository;

import escuelaing.edu.co.truckdar.route_ai_agent.model.RouteDecisionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteDecisionLogRepository extends JpaRepository<RouteDecisionLog, Long> {
    List<RouteDecisionLog> findByVehiclePlateOrderByCreatedAtDesc(String vehiclePlate);
}
