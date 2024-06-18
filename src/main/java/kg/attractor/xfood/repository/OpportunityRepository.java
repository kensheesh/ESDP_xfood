package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findAllByUserEmailAndDateBetween(String userEmail, LocalDateTime after, LocalDateTime before);
}
