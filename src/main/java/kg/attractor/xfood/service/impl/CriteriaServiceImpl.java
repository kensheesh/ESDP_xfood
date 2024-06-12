package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.model.Zone;
import kg.attractor.xfood.repository.CriteriaRepository;
import kg.attractor.xfood.repository.SectionRepository;
import kg.attractor.xfood.repository.ZoneRepository;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriteriaServiceImpl implements CriteriaService {
    private final CriteriaRepository criteriaRepository;
    private final DtoBuilder dtoBuilder;
    private final SectionRepository sectionRepository;
    private final ZoneRepository zoneRepository;

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

    @Override
    public Long create(CriteriaSupervisorCreateDto createDto) {
        log.info("COEFFICIENT {}", createDto.getCoefficient());
        if (createDto.getCoefficient()==null) {
            createDto.setCoefficient(1);
        }
        return  criteriaRepository.save(Criteria.builder()
                        .section(sectionRepository.findByName(createDto.getSection()))
                        .description(createDto.getDescription())
                        .zone(zoneRepository.findByName(createDto.getZone()))
                        .coefficient(createDto.getCoefficient())
                .build()).getId();
    }

    @Override
    public Map<String, String> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.info(errors.toString());
        return errors;
    }
}
