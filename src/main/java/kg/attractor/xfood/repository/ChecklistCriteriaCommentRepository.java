package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckListsCriteriaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistCriteriaCommentRepository extends JpaRepository<CheckListsCriteriaComment, Long> {
    List<CheckListsCriteriaComment> getAllByChecklistCriteria_Id(Long checklistCriteriaId);
}
