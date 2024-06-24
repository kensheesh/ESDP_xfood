package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaDto;
import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;

import java.util.List;

import kg.attractor.xfood.model.CheckListsCriteria;

public interface CheckListCriteriaService {

    void save(List<SaveCriteriaDto> saveCriteriaDto);

    void save(CheckListsCriteria checkListsCriteria);

    CheckListCriteriaDto createNewFactor(SaveCriteriaDto saveCriteriaDto);

    void deleteFactor(Long id, Long checkListId);

    CheckListCriteriaDto createCritFactor(SaveCriteriaDto saveCriteriaDto, String description);
}
