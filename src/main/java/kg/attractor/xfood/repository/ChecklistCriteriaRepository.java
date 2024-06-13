package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistCriteriaRepository  extends JpaRepository<CheckListsCriteria, Long> {
}
