package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.repository.CriteriaRepository;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriteriaServiceImpl implements CriteriaService {
    private final CriteriaRepository criteriaRepository;
    private final DtoBuilder dtoBuilder;

    @Override
    public List<CriteriaSupervisorShowDto> getCriterion(String type) {
       if (type!=null && !type.isEmpty()) {
           return criteriaRepository.findCriteriaByCriteriaTypes(type).stream().map(dtoBuilder::buildCriteriaShowDto).toList();
       }
       else return new ArrayList<>();
    }

    @Override
    public List<CriteriaSupervisorShowDto> getByDescription(String description) {
        if (description!=null && !description.isEmpty()) {
            return criteriaRepository.findCriterionByDescriptionContainingIgnoreCase(description).stream().map(dtoBuilder::buildCriteriaShowDto).toList();
        }
        return new ArrayList<>();
    }

    @Override
    public CriteriaSupervisorShowDto getById(Long id) {
        return dtoBuilder.buildCriteriaShowDto(Objects.requireNonNull(criteriaRepository.findById(id).orElse(null)));
    }
}
