package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

}
