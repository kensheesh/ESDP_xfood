package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckTypeFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface CheckTypeFeeRepository extends JpaRepository<CheckTypeFee, Long> {

    @Query("""
                select ctf.fees from CheckTypeFee ctf where ctf.checkType.id = :typeId
            """)
    BigDecimal findFeeByTypeId(Long typeId);
}