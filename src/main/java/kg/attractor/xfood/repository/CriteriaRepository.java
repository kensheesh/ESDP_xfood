package kg.attractor.xfood.repository;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.model.CriteriaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
    @Query(value = "select c from Criteria c JOIN CriteriaType ct on c.id = ct.criteria.id JOIN CriteriaPizzeria cp on c.id = cp.criteria.id where  ct.type.id = :checkTypeId and cp.pizzeria.id=:pizzeriaId")
    List<Criteria> findCriteriaByCriteriaTypeAndCriteriaPizzeria(Long checkTypeId, Long pizzeriaId);

    List<Criteria> findCriterionByDescriptionContainingIgnoreCase(String description);

    @Query(value = "select c from Criteria c JOIN CriteriaType ct on c.id = ct.criteria.id  where  ct.type.id = :checkTypeId")
    List<Criteria> findCriteriaByCriteriaType(Long checkTypeId);

    @Query(value = "select c from Criteria c where c.section.id = 2")
    List<Criteria> findCriteriaWhereSectionWow();
}
