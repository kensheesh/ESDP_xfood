package kg.attractor.xfood.service;

import kg.attractor.xfood.model.CriteriaType;

import java.util.List;

public interface CriteriaTypeService {

    void save(CriteriaType criteriaType);

    List<CriteriaType> findAllByTypeId(Long checkTypeId);
}
