package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckListsCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckListCriteriaRepository extends JpaRepository<CheckListsCriteria, Long> {

    List<CheckListsCriteria> findByChecklistId(Long id);

    @Query(
            "select clc from CheckListsCriteria clc " +
                    "where clc.checklist.id = :checkListId and clc.criteria.id = :criteriaId"
    )
    Optional<CheckListsCriteria> findByCheckListIdAndCriteriaId(Long checkListId, Long criteriaId);
}
