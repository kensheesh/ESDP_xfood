package kg.attractor.xfood.repository;

import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
	
	List<CheckList> findByOpportunityUserEmailAndStatus(String username, Status status);
	
}
