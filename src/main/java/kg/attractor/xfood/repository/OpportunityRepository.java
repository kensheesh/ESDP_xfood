package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    @Query("select o from Opportunity o where o.date = ?1")
    List<Opportunity> findByDate(LocalDateTime date);

    @Query("select o from Opportunity o where o.user.id = ?1 and o.date = ?2 order by o.startTime")
    List<Opportunity> findByUser_IdAndDateOrderByStartTimeAsc(Long id, LocalDateTime date);
}
