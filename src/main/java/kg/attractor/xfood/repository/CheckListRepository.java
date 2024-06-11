package kg.attractor.xfood.repository;

import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {

	@Query(value = """
			SELECT c
			FROM CheckList c
			        JOIN Opportunity o ON c.opportunity.id = o.id
			         JOIN User u ON o.user.id = u.id
			WHERE u.email = :#{#username} AND CAST(c.status as text) = :#{#status.getStatus()}
			""")
	List<CheckList> findCheckListByExpertEmailAndStatus(String username, Status status);
	
}

