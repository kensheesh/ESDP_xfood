package kg.attractor.xfood.service;

import kg.attractor.xfood.model.CheckTypeFee;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CheckTypeFeeService {

    BigDecimal getFeesByCheckTypeId(Long checkTypeId);

    void save(CheckTypeFee checkTypeFee);

    CheckTypeFee getCheckTypeFeeByTypeId(Long id);
}
