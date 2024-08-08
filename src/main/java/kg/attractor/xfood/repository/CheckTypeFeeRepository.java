package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckTypeFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface CheckTypeFeeRepository extends JpaRepository<CheckTypeFee, Long> {

    @Query("""
                select ctf.fees from CheckTypeFee ctf where ctf.checkType.id = :typeId
            """)
    BigDecimal findFeeByTypeId(Long typeId);

    @Query("select c from CheckTypeFee c where c.checkType.id = ?1 and c.enabled = true")
    Optional<CheckTypeFee> findByCheckType_IdAndEnabledTrue(Long id);
}