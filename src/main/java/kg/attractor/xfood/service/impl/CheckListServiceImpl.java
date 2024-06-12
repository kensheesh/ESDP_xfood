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
import kg.attractor.xfood.repository.PizzeriaRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    public List<CheckListAnalyticsDto> getAnalytics(String pizzeria, String manager, String expert, LocalDate startDate, LocalDate endDate) {

        List<CheckList> checkLists = checkListRepository.findByStatus(Status.DONE);

        return checkLists.stream().map(checkList -> {
            CheckListAnalyticsDto dto = new CheckListAnalyticsDto();

            dto.setId(checkList.getId());
            dto.setPizzeria(checkList.getWorkSchedule().getPizzeria());
            dto.setManager(checkList.getWorkSchedule().getManager());
            dto.setExpert(checkList.getOpportunity().getUser());
            dto.setDate(checkList.getWorkSchedule().getDate().toLocalDate());

            List<CheckListsCriteria> criterias = checkListsCriteriaRepository.findAllByChecklistId(checkList.getId());

            int maxValue = criterias.stream()
                    .filter(criteria -> criteria.getMaxValue() != null)
                    .mapToInt(CheckListsCriteria::getMaxValue)
                    .sum();

            int value = criterias.stream()
                    .mapToInt(CheckListsCriteria::getValue)
                    .sum();

            double result = maxValue == 0 ? 0 : Math.round(((double) value / maxValue) * 100);

            dto.setResult((int) result);
            return dto;
        }).collect(Collectors.toList());
    }


}











