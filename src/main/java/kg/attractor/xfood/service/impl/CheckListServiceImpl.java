package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {

    private final CheckListRepository checkListRepository;
    private final DtoBuilder dtoBuilder;
    private final ChecklistCriteriaRepository checkListsCriteriaRepository;

    @Override
    public List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status) {
        return checkListRepository.findCheckListByExpertEmailAndStatus(username, status)
                .stream()
                .map(dtoBuilder::buildChecklistDto)
                .toList();
    }

    @Override
    public CheckListResultDto getResult(Long checkListId) {
        return dtoBuilder.buildCheckListResultDto(
                checkListRepository.findByIdAndStatus(checkListId, Status.DONE)
                        .orElseThrow(() -> new NotFoundException("Check list not found"))
        );
    }

    @Override
    public List<CheckListAnalyticsDto> getAnalytics(String pizzeriaId, String managerId, String expertId, LocalDate startDate, LocalDate endDate) {
        List<CheckList> checkLists = checkListRepository.findByStatus(Status.DONE);
        System.out.println(pizzeriaId);
        System.out.println(managerId);
        System.out.println(expertId);
        // Фильтрация по пиццерии
        if (!"default".equals(pizzeriaId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getPizzeria().getId().equals(Long.parseLong(pizzeriaId)))
                    .collect(Collectors.toList());

        }

        // Фильтрация по менеджеру
        if (!"default".equals(managerId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getManager().getId().equals(Long.parseLong(managerId)))
                    .collect(Collectors.toList());
        }

        // Фильтрация по эксперту
        if (!"default".equals(expertId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getOpportunity().getUser().getId().equals(Long.parseLong(expertId)))
                    .collect(Collectors.toList());
        }

        // Фильтрация по датам
        if (startDate != null && endDate != null) {
            checkLists = checkLists.stream()
                    .filter(checkList -> {
                        LocalDate date = checkList.getWorkSchedule().getDate().toLocalDate();
                        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                                (date.isEqual(endDate) || date.isBefore(endDate));
                    })
                    .collect(Collectors.toList());
        }

        List<CheckListAnalyticsDto> checkListAnalyticsDtoList = new ArrayList<>();

        for (CheckList checkList : checkLists) {
            CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();
            checkListAnalyticsDto.setId(checkList.getId());
            checkListAnalyticsDto.setPizzeria(checkList.getWorkSchedule().getPizzeria());
            checkListAnalyticsDto.setManager(checkList.getWorkSchedule().getManager());
            checkListAnalyticsDto.setExpert(checkList.getOpportunity().getUser());
            checkListAnalyticsDto.setDate(checkList.getWorkSchedule().getDate().toLocalDate());

            List<CheckListsCriteria> criterias = checkListsCriteriaRepository.findAllByChecklistId(checkList.getId());
            int maxvalue = 0;
            int value = 0;
            for (CheckListsCriteria criteria : criterias) {
                if (criteria.getMaxValue() != null) {
                    maxvalue += criteria.getMaxValue();
                }
                value += criteria.getValue();
            }
            double result = Math.round(((double) value / maxvalue) * 100);
            checkListAnalyticsDto.setResult((int) result);

            checkListAnalyticsDtoList.add(checkListAnalyticsDto);
        }

        return checkListAnalyticsDtoList;
    }

}











