package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;

import java.util.List;

public interface CriteriaService {

    List<CriteriaSupervisorShowDto> getCriterion(String type);

    List<CriteriaSupervisorShowDto> getByDescription(String description);

    CriteriaSupervisorShowDto getById(Long id);
}
