package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.CheckListMiniSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;

import java.time.LocalDate;
import java.util.List;

public interface CheckListService {

    ChecklistShowDto getCheckListById(Long id);

    CheckList getModelCheckListById(Long id);

    void save(CheckList checkList);

	List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status);

    List<ChecklistMiniExpertShowDto> getUsersChecklists(Status status);

    CheckListResultDto getResult(Long checkListId);

    CheckListMiniSupervisorCreateDto create(CheckListSupervisorCreateDto createDto);

    void bindChecklistWithCriterion(CheckListMiniSupervisorCreateDto checklistDto);

    List<CheckListAnalyticsDto> getAnalytics(String pizzeria, String manager, String expert, LocalDate startDate, LocalDate endDate);

    CheckListResultDto getResultByUuidLink(String uuidLink);
}
