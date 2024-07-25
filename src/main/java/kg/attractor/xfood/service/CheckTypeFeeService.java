package kg.attractor.xfood.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CheckTypeFeeService {

    BigDecimal getFeesByCheckTypeId(Long checkTypeId);
}
