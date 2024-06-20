package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

    @Query("select w from WorkSchedule  w where w.manager.id = :managerId and FUNCTION('DATE', w.startTime) = :startTime")
    Optional<WorkSchedule> findByManager_IdAndStartTimeDate(Long managerId, LocalDate startTime);

    @Query("select w from WorkSchedule w where w.pizzeria.id = ?1 and w.startTime = ?2 and w.endTime = ?3")
    List<WorkSchedule> findByPizzeria_IdAndStartTimeAndEndTime(Long id, LocalDateTime startTime, LocalDateTime endTime);

    @Query("""
            select w from WorkSchedule w
            where w.pizzeria.id = ?1 and FUNCTION('DATE', w.startTime) between ?2 and ?3
            order by w.manager.surname""")
    List<WorkSchedule> findByPizzeria_IdAndStartTimeBetweenOrderByManager_SurnameAsc(Long id, LocalDate startTimeStart, LocalDate startTimeEnd);

    @Query("""
            select w from WorkSchedule w
            where w.manager.id = ?1 and w.pizzeria.id = ?2 and FUNCTION('DATE', w.startTime) = ?3
            """)
    Optional<WorkSchedule> findByManager_IdAndPizzeria_IdAndStartTime(Long id, Long id1, LocalDate startTime);
}
