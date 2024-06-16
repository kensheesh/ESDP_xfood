package kg.attractor.xfood.repository;

import jakarta.transaction.Transactional;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.WorkSchedule;
import org.hibernate.annotations.SQLSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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

	@Query(value = """
			SELECT c
			FROM CheckList c
			where c.id = ?1
			and CAST(c.status as text) = :#{#status.getStatus()}
			""")
	Optional<CheckList> findByIdAndStatus(Long checkListId, Status status);
	@Modifying
	@Transactional
	@Query(value = """
			insert into check_lists(status, work_schedule_id, opportunity_id)
 			values(CAST(CAST(:#{#status} as text) as Status),:#{#workSchedule},:#{#opportunity}) ;
	""", nativeQuery = true)
	int saveChecklist(Long opportunity, Long workSchedule, String status);

	/*@Transactional
	@Query(value = "SELECT * FROM check_lists WHERE status = CAST(CAST(:status AS text) AS Status) AND work_schedule_id = :workSchedule AND opportunity_id = :opportunity", nativeQuery = true)
	CheckList findCheckListById(String status, Long workSchedule, Long opportunity);

*/

	CheckList findCheckListByWorkSchedule_IdAndAndOpportunity_Id(Long workScheduleId, Long opportunityId);
}

