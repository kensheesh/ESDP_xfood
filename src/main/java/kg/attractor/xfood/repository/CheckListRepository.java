package kg.attractor.xfood.repository;

import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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

    @Query(value = """
		    SELECT c
		    FROM CheckList c
		    WHERE CAST(c.status as text) = :#{#status.getStatus()}
		    """)
    List<CheckList> findByStatus(Status status);
    
    @Query(value = """
		    SELECT c
		    FROM CheckList c
		            JOIN Opportunity o ON c.opportunity.id = o.id
		             JOIN User u ON o.user.id = u.id
		    WHERE CAST(c.status as text) = :#{#status.getStatus()}
		    """)
    List<CheckList> findCheckListByStatus(Status status);
}

