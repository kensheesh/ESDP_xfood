package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checks.CheckListCriteriaDto;
import kg.attractor.xfood.dto.checks.CheckListDto;
import kg.attractor.xfood.dto.criteria.CriteriaDto;
import kg.attractor.xfood.dto.criteria.ZoneDto;
import kg.attractor.xfood.dto.pizzeria.ManagerDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.work_schedule.WorkScheduleDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.repository.CheckListCriteriaRepository;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;
    private final CheckListCriteriaRepository checkListCriteriaRepository;

    private static final String PATTERN_FORMAT = "dd.MM.yyyy";

    @Override
    public CheckListDto getCheckListById(Long id) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow();
        List<CheckListsCriteria> checkListsCriteria = checkListCriteriaRepository.findByChecklistId(id);
        List<CheckListCriteriaDto> checkListCriteriaDtos = new ArrayList<>();

        checkListsCriteria.forEach(c -> {
            checkListCriteriaDtos.add(CheckListCriteriaDto.builder()
                    .criteria(CriteriaDto.builder()
                            .id(c.getCriteria().getId())
                            .coefficient(c.getCriteria().getCoefficient())
                            .description(c.getCriteria().getDescription())
                            .zone(ZoneDto.builder()
                                    .name(c.getCriteria().getZone().getName())
                                    .build())
                            .maxValue(c.getCriteria().getMaxValue())
                            .section(c.getCriteria().getSection())
                            .build())
                    .build());

        });

        Manager manager = checkList.getWorkSchedule().getManager();
        Pizzeria pizzeria = checkList.getWorkSchedule().getPizzeria();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

        return CheckListDto.builder()
                .id(id)
                .workSchedule(WorkScheduleDto.builder()
                        .manager(ManagerDto.builder()
                                .name(manager.getName())
                                .phoneNumber(manager.getPhoneNumber())
                                .surname(manager.getSurname())
                                .build())

                        .pizzeria(PizzeriaDto.builder()
                                .location(pizzeria.getLocation())
                                .name(pizzeria.getName())
                                .build())
                        .date(formatter.format(checkList.getWorkSchedule().getDate()))
                        .build())
                .checkListsCriteriaList(checkListCriteriaDtos)
                .build();
    }
}
