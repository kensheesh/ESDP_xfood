package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.model.Criteria;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface CriteriaService {

    List<CriteriaSupervisorShowDto> getByDescription(String description, boolean isUsual);

    CriteriaSupervisorShowDto getById(Long id);
    CriteriaSupervisorShowDto getByCheckAndCriteria(Long checkId, Long criteriaId);

    Criteria findById(Long id);

    Long create(CriteriaSupervisorCreateDto createDto);

    Map<String, String> handleValidationErrors(BindingResult bindingResult);

    List<CriteriaSupervisorShowDto> getByCheckType(Long checkTypeId);
    Criteria getCriteriaById(Long criteriaId);

    List<CriteriaSupervisorShowDto> getWowCriteria();

    List<CriteriaSupervisorShowDto> getCritCriteria();

}
