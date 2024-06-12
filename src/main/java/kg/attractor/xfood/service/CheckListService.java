package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface CheckListService {

    List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status);

    CheckListResultDto getResult(Long checkListId);

    List<CheckListAnalyticsDto> getAnalytics(
            String pizzeria, String manager, String expert, LocalDate startDate, LocalDate endDate);

}
