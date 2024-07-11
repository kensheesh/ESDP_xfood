package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.CheckListSupervisorEditDto;
import kg.attractor.xfood.dto.checklist.*;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CheckListService {

    ChecklistShowDto getCheckListById(String id);

    CheckList getModelCheckListById(String id);
    CheckList getModelCheckListById(Long id);

    void save(CheckList checkList);

	List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status);

    List<ChecklistMiniExpertShowDto> getUsersChecklists(Status status);

    CheckListResultDto getResult(String checkListId);
    CheckListResultDto getResult(Long checkListId);

    CheckListMiniSupervisorCreateDto create(CheckListSupervisorCreateDto createDto);

    void bindChecklistWithCriterion(CheckListMiniSupervisorCreateDto checklistDto);

    List<CheckListAnalyticsDto> getAnalytics(String pizzeria, String manager, String expert, LocalDate startDate, LocalDate endDate);

    CheckListResultDto getResultByUuidLink(String uuidLink);
    
    CheckList updateCheckStatusCheckList(String id, LocalTime localTime);

    CheckListSupervisorEditDto getChecklistByUuid(String uuid);

    void edit(CheckListSupervisorEditDto checkList);

    Integer getMaxPoints(Long id);
    Integer getPercentageById(Long id);
}
