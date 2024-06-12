package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;


import java.util.List;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.model.CheckList;

public interface CheckListService {

    ChecklistShowDto getCheckListById(Long id);
    CheckList getModelCheckListById(Long id);

    void save(CheckList checkList);

	List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status);

    CheckListResultDto getResult(Long checkListId);
}
