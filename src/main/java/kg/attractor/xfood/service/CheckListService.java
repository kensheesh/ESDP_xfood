package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.CheckListSupervisorEditDto;
import kg.attractor.xfood.dto.checklist.*;
import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.dto.statistics.DayDto;
import kg.attractor.xfood.dto.statistics.StatisticsDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    
    CheckList updateCheckStatusCheckList(String id);

    CheckListSupervisorEditDto getChecklistByUuid(String uuid, String type);

    void edit(CheckListSupervisorEditDto checkList);

    Integer getMaxPoints(Long id);
    
    Integer getPercentageById(Long id);
    List<CheckListRewardDto> getChecklistRewardsByExpert(String expertEmail, LocalDateTime startDate, LocalDateTime endDate, String pizzeriaName);

    StatisticsDto getStatistics(LocalDate from, LocalDate to);



    void comment(String uuid, Long criteriaId, CommentDto commentDto);
    void delete(String uuid);

    List<ChecklistMiniExpertShowDto> getDeletedChecklists();

    void restore(String uuid);

    ChecklistShowDto getCheckListByIdIncludeDeleted(String checkListId);

    long getAmountOfNewChecks();
}
