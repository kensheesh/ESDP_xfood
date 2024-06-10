package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.repository.CriteriaRepository;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriteriaServiceImpl implements CriteriaService {
    private final CriteriaRepository criteriaRepository;
    private final DtoBuilder dtoBuilder;

    @Override
    public List<CriteriaSupervisorShowDto> getCriterias() {
        return criteriaRepository.findAll().stream().map(dtoBuilder::buildCriteriaShowDto).toList();
    }
}
