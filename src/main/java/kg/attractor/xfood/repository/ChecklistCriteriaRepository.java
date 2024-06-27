package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistCriteriaRepository extends JpaRepository<CheckListsCriteria, Long> {

    List<CheckListsCriteria> findAllByChecklistId(Long id);

    @Query("select clc from CheckListsCriteria clc " +
            "where clc.checklist.id = :checkListId and clc.criteria.id = :criteriaId")
    Optional<CheckListsCriteria> findByCheckListIdAndCriteriaId(Long checkListId, Long criteriaId);

    @Query("select clc from CheckListsCriteria clc where clc.checklist.id = :checkListId")
    List<CheckListsCriteria> findCriteriaByCheckListId(Long checkListId);
}
