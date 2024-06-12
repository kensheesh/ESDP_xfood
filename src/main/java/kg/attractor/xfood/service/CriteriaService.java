package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface CriteriaService {

    List<CriteriaSupervisorShowDto> getCriterion(String type);

    List<CriteriaSupervisorShowDto> getByDescription(String description);

    CriteriaSupervisorShowDto getById(Long id);

    Long create(CriteriaSupervisorCreateDto createDto);

    Map<String, String> handleValidationErrors(BindingResult bindingResult);
}
