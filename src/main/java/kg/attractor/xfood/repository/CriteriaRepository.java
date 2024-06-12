package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.model.CriteriaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
    @Query(value = "select c from Criteria c JOIN CriteriaType ct on c.id = ct.criteria.id where  ct.type.name = :criteriaType")
    List<Criteria> findCriteriaByCriteriaTypes(String criteriaType);

    List<Criteria> findCriterionByDescriptionContainingIgnoreCase(String description);
}
