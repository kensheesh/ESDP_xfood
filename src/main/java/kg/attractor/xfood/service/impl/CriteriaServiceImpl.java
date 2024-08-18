package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.model.CriteriaType;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.repository.CriteriaRepository;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final SectionService sectionService;
    private final ZoneService zoneService;
    private final CriteriaTypeService criteriaTypeService;
    private final ChecklistCriteriaRepository checklistCriteriaRepository;



    @Override
    public List<CriteriaSupervisorShowDto> getByDescription(String description, boolean shouldInclude) {
        if (description!=null && !description.isEmpty()) {
            List<Criteria> criteria = criteriaRepository.findCriterionByDescriptionContainingIgnoreCase(description);
            if (!shouldInclude) {
                criteria.removeIf(criteria1 -> Objects.equals(criteria1.getSection().getName(), "Критический фактор") || Objects.equals(criteria1.getSection().getName(), "WOW фактор") );
            }
            return criteria.stream().map(dtoBuilder::buildCriteriaSupervisorShowDto).toList();

        }
        return new ArrayList<>();
    }

    @Override
    public CriteriaSupervisorShowDto getById(Long id) {
        return dtoBuilder.buildCriteriaSupervisorShowDto(Objects.requireNonNull(criteriaRepository.findById(id).orElse(null)));
    }

    @Override
    public CriteriaSupervisorShowDto getByCheckAndCriteria(Long CheckId, Long CriteriaID) {
           CheckListsCriteria checkListsCriteria = checklistCriteriaRepository.findByCriteria_IdAndAndChecklist_Id(CriteriaID, CheckId).orElse(null);
           if (checkListsCriteria == null) {
               Criteria criteria = criteriaRepository.findById(CriteriaID).orElseThrow(()-> new NoSuchElementException("Критерий не найден с айди "+CriteriaID));

               CriteriaSupervisorShowDto c = CriteriaSupervisorShowDto.builder()
                       .section(criteria.getSection().getName())
                       .value(1)
                       .coefficient(criteria.getCoefficient())
                       .id(criteria.getId())
                       .description(criteria.getDescription())
                       .maxValue(1)
                       .build();
               return c;
           }
           CriteriaSupervisorShowDto c = CriteriaSupervisorShowDto.builder()
                   .section(checkListsCriteria.getCriteria().getSection().getName())
                   .value(checkListsCriteria.getValue())
                   .coefficient(checkListsCriteria.getCriteria().getCoefficient())
                   .id(checkListsCriteria.getCriteria().getId())
                   .description(checkListsCriteria.getCriteria().getDescription())
                   .maxValue(checkListsCriteria.getMaxValue())
                   .build();
           return c;
    }

    @Override
    public Criteria findById(Long id) {
        return criteriaRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such  criteria  by id "+id));
    }

    @Override
    public Long create(CriteriaSupervisorCreateDto createDto) {
        log.info("coefficient {}", createDto.getCoefficient());
        if (createDto.getCoefficient()==null) {
            createDto.setCoefficient(1);
        }
        return  criteriaRepository.save(Criteria.builder()
                        .section(sectionService.findByName(createDto.getSection()))
                        .description(createDto.getDescription())
                        .zone(zoneService.findByName(createDto.getZone()))
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

    @Override
    public List<CriteriaSupervisorShowDto> getByCheckType(Long checkTypeId) {
        List <CriteriaType> criterion = criteriaTypeService.findAllByTypeId(checkTypeId);
        criterion.removeIf(criteriaType -> !Objects.equals(criteriaType.getCriteria().getSection().getName(), ""));
        log.info(criterion.toString());
        List<CriteriaSupervisorShowDto> dtos = new ArrayList<>();
        for (CriteriaType criteriaType : criterion) {
            CriteriaSupervisorShowDto supervisorShowDto = new CriteriaSupervisorShowDto();
            dtos.add(CriteriaSupervisorShowDto
                    .builder()
                            .maxValueType(criteriaType.getMaxValue())
                            .id(criteriaType.getCriteria().getId())
                            .section(criteriaType.getCriteria().getSection().getName())
                            .description(criteriaType.getCriteria().getDescription())
                            .coefficient(criteriaType.getCriteria().getCoefficient())
                            .zone(criteriaType.getCriteria().getZone().getName())
                    .build());
        }
        return dtos;
    }

    @Override
    public Criteria getCriteriaById(Long criteriaId) {
        return criteriaRepository.findById(criteriaId)
                .orElseThrow(() -> new NoSuchElementException("Критерия с ID: " + criteriaId + " не найдена!"));
    }

    @Override
    public List<CriteriaSupervisorShowDto> getWowCriteria() {
        return criteriaRepository.findCriteriaWhereSectionWow()
                .stream().map(dtoBuilder::buildCriteriaSupervisorShowDto)
                .toList();
    }

    @Override
    public List<CriteriaSupervisorShowDto> getCritCriteria() {
        return criteriaRepository.findCriteriaWhereSectionCrit()
                .stream().map(dtoBuilder::buildCriteriaSupervisorShowDto)
                .toList();
    }
}