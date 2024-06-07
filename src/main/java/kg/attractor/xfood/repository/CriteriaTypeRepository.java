package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CriteriaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaTypeRepository extends JpaRepository<CriteriaType, Long> {
}
