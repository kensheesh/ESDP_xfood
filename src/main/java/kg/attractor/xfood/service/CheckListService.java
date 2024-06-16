package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.CheckListMiniSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;


import java.util.List;

public interface CheckListService {
	
	List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status);

    CheckListResultDto getResult(Long checkListId);

    CheckListMiniSupervisorCreateDto create(CheckListSupervisorCreateDto createDto);

    void bindChecklistWithCriterion(CheckListMiniSupervisorCreateDto checklistDto);
}
