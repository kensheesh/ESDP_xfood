package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CriteriaPizzeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaPizzeriaRepository extends JpaRepository<CriteriaPizzeria, Long> {
}
