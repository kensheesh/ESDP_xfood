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
  
//    List<WorkSchedule> findByPizzeria_IdAndDateBetween(Long id, LocalDateTime dateStart, LocalDateTime dateEnd);
//
//    Optional<WorkSchedule> findByManagerAndDate(Manager manager, LocalDateTime date);
//
//    @Query("SELECT ws FROM WorkSchedule ws WHERE ws.manager.id = :managerId AND EXTRACT(YEAR FROM ws.date) = :year AND EXTRACT(MONTH FROM ws.date) = :month AND EXTRACT(DAY FROM ws.date) = :day")
//    Optional<WorkSchedule> findByManagerIdAndDate(@Param("managerId") Long managerId, @Param("year") int year, @Param("month") int month, @Param("day") int day);
}
