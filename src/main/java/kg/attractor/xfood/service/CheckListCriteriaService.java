package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;

import java.util.List;

public interface CheckListCriteriaService {

    void save(List<SaveCriteriaDto> saveCriteriaDto);
}
