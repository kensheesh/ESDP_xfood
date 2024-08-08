package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.CheckType;
import kg.attractor.xfood.model.CheckTypeFee;
import kg.attractor.xfood.repository.CheckTypeFeeRepository;
import kg.attractor.xfood.service.CheckTypeFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckTypeFeeServiceImpl implements CheckTypeFeeService {

    private final CheckTypeFeeRepository checkTypeFeeRepository;


    @Override
    public BigDecimal getFeesByCheckTypeId(Long checkTypeId) {
        return checkTypeFeeRepository.findFeeByTypeId(checkTypeId);
    }

    public BigDecimal getEnabledFeeByCheckTypeId(Long checkTypeID) {
        return checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(checkTypeID)
                .orElseThrow(() -> new NoSuchElementException("Для данной проверки вознаграждение не найдено")).getFees();
    }
}
