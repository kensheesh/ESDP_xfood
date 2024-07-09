package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    @Query("select s from Shift s where s.opportunity.id = ?1 order by s.startTime")
    List<Shift> findByOpportunity_IdOrderByStartTimeAsc(Long id);
}
