package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.model.CheckList;

public interface CheckListService {

    ChecklistShowDto getCheckListById(Long id);
    CheckList getModelCheckListById(Long id);

    void save(CheckList checkList);
}
