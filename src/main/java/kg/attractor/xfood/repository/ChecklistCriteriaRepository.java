package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckListsCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistCriteriaRepository extends JpaRepository<CheckListsCriteria, Long> {

    List<CheckListsCriteria> findAllByChecklistId(Long id);
}
