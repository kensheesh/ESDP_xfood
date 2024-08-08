package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.exception.FeesNotFoundException;
import kg.attractor.xfood.repository.CheckTypeFeeRepository;
import kg.attractor.xfood.service.CheckTypeFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckTypeFeeServiceImpl implements CheckTypeFeeService {

    private final CheckTypeFeeRepository checkTypeFeeRepository;

    @Override
    public BigDecimal getFeesByCheckTypeId(Long checkTypeId) {
        return getEnabledFeeByCheckTypeId(checkTypeId);
    }

    public BigDecimal getEnabledFeeByCheckTypeId(Long checkTypeID) {
        return checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(checkTypeID)
                .orElseThrow(() -> new FeesNotFoundException("Тарифы не найдены, ошибка")).getFees();
    }
}
