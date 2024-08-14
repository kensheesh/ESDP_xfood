package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckListsCriteriaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistCriteriaCommentRepository extends JpaRepository<CheckListsCriteriaComment, Long> {
    List<CheckListsCriteriaComment> getAllByChecklistCriteria_Id(Long checklistCriteriaId);

    Optional<CheckListsCriteriaComment> findFirstByChecklistCriteria_IdAndComment_Id(Long checklistCriteriaId, Long commentId);

    boolean existsByChecklistCriteria_IdAndComment_Id(Long checklistCriteriaId, Long commentId);

}
