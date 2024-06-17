package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.CriteriaType;
import kg.attractor.xfood.repository.CriteriaTypeRepository;
import kg.attractor.xfood.service.CriteriaTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriteriaTypeServiceImpl implements CriteriaTypeService {
    private final CriteriaTypeRepository criteriaTypeRepository;

    @Override
    public void save(CriteriaType criteriaType) {
        criteriaTypeRepository.save(criteriaType);
        log.info("CriteriaType saved {}", criteriaType);
    }
}
