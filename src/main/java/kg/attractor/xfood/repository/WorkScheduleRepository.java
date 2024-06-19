package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
  
    List<WorkSchedule> findByPizzeria_IdAndDateBetween(Long id, LocalDateTime dateStart, LocalDateTime dateEnd);
   
    Optional<WorkSchedule> findByManagerAndDate(Manager manager, LocalDateTime date);

    Optional<WorkSchedule> findByManager_IdAndStartTimeAndEndTime(Long managerId, LocalDateTime startTime, LocalDateTime endTime);
}
