package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;

import java.util.List;

import kg.attractor.xfood.model.CheckListsCriteria;

public interface CheckListCriteriaService {

    void save(List<SaveCriteriaDto> saveCriteriaDto);

    void save(CheckListsCriteria checkListsCriteria);

    Long createWowFactor(SaveCriteriaDto saveCriteriaDto);
}
