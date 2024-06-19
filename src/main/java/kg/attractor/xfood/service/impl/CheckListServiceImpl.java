package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import jakarta.persistence.*;
import kg.attractor.xfood.dto.checklist.CheckListMiniSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.IncorrectDateException;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.repository.OpportunityRepository;
import kg.attractor.xfood.service.*;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
    private final UserRepository userRepository;
    private final WorkScheduleService workScheduleService;
    private final DtoBuilder dtoBuilder;
    private final UserService userService;
    private final OpportunityService opportunityService;
    private final CheckTypeService checkTypeService;
    private final CriteriaService criteriaService;
    private final CriteriaTypeService criteriaTypeService;
    //	private final CheckListCriteriaService checkListsCriteriaService;
    private final CriteriaPizzeriaService criteriaPizzeriaService;
    private final EntityManager entityManager;
    private final OpportunityRepository opportunityRepository;
    private final CheckListRepository checkListRepository;
    private final ChecklistCriteriaRepository checklistCriteriaRepository;


    @Override
    public List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status) {
        return checkListRepository.findCheckListByExpertEmailAndStatus(username, status)
                .stream()
                .map(dtoBuilder::buildChecklistDto)
                .toList();
    }

    @Override
    @Transactional
    public CheckListMiniSupervisorCreateDto create(CheckListSupervisorCreateDto createDto) {
        if (createDto.getCriteriaMaxValueDtoList().isEmpty()) {
            throw new IncorrectDateException("Чек лист не содержит критериев");
        }
        if (createDto.getStartTime().isAfter(createDto.getEndTime())) {
            throw new IncorrectDateException("Время начала не может быть позже время конца смены");
        }
        WorkSchedule workSchedule = workScheduleService.findWorkScheduleByManagerAndDate(createDto.getManagerId(), createDto.getManagerStartTime(), createDto.getManagerEndTime());
        log.info(workSchedule.getStartTime().toString());
        log.info(createDto.getEndTime().toString());
        if (createDto.getEndTime().isBefore(workSchedule.getStartTime().toLocalTime())){
            throw new IncorrectDateException("Время начала смены менеджера не может быть позже времени окончания работы эксперта");
        }
        //TODO уточнить надо ли делать проверку по work_schedule, opportunity and type и если необходимо добавить
        createDto.getCriteriaMaxValueDtoList().removeIf(criteriaMaxValueDto -> criteriaMaxValueDto.getCriteriaId() == null);
        createDto.getCriteriaMaxValueDtoList().sort(Comparator.comparing(CriteriaMaxValueDto::getCriteriaId));

        LocalDate startDate = createDto.getManagerStartTime().toLocalDate();
        LocalDate endDate = createDto.getManagerEndTime().toLocalDate();

        Date date;
        if (!startDate.equals(endDate)) {
            date = Date.from(createDto.getManagerEndTime().atZone(ZoneId.systemDefault()).toInstant());
        } else {
            date = Date.from(createDto.getManagerStartTime().atZone(ZoneId.systemDefault()).toInstant());
        }
        Opportunity opportunity = Opportunity.builder()
                .user(userService.findById(createDto.getExpertId()))
                .date(date)
                .startTime(createDto.getStartTime())
                .endTime(createDto.getEndTime())
                .build();

        Long id = opportunityRepository.save(opportunity).getId();
        checkListRepository.saveChecklist(id, workSchedule.getId(), Status.NEW.getStatus());
        checkListRepository.flush();
        return CheckListMiniSupervisorCreateDto.builder().opportunityId(id).workScheduleId(workSchedule.getId()).criteriaMaxValueDtoList(createDto.getCriteriaMaxValueDtoList()).pizzeria(workSchedule.getPizzeria()).build();
    }

    @Override
    public ChecklistShowDto getCheckListById(Long id) {
        CheckList checkList = getModelCheckListById(id);
        return dtoBuilder.buildChecklistShowDto(checkList);
    }

    @Override
    public CheckList getModelCheckListById(Long id) {
        return checkListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Чек-лист с ID:" + id + " не найден"));
    }

    @Override
    public void save(CheckList checkList) {
        checkListRepository.save(checkList);
    }

    @Override
    public List<ChecklistMiniExpertShowDto> getUsersChecklists(Status status) {
        return checkListRepository.findCheckListByStatus(status)
                .stream()
                .map(dtoBuilder::buildChecklistDto)
                .toList();
    }


    @Override
    public CheckListResultDto getResult(Long checkListId) {
        CheckList checkList = getModelCheckListById(checkListId);

        if(checkList.getStatus().equals(Status.DONE) || checkList.getStatus().equals(Status.IN_PROGRESS)) {
            return dtoBuilder.buildCheckListResultDto(
                    checkListRepository.findByIdAndStatus(checkListId, checkList.getStatus())
                            .orElseThrow(() -> new NotFoundException("Check list not found"))
            );
        }

        throw new IllegalArgumentException("По данной проверке нет еще результатов");
    }

    @Override
    public List<CheckListAnalyticsDto> getAnalytics(String pizzeriaId, String managerId, String expertId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByEmail(AuthParams.getPrincipal().getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
        List<CheckList> checkLists;
        if (user.getRole().equals(Role.EXPERT)) {
            checkLists = checkListRepository.findCheckListByExpertEmailAndStatus(user.getEmail(), Status.DONE);
        } else {
            checkLists = checkListRepository.findByStatus(Status.DONE);
        }

        if (!"default".equals(pizzeriaId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getPizzeria().getId().equals(Long.parseLong(pizzeriaId)))
                    .collect(Collectors.toList());

        }
        if (!"default".equals(managerId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getManager().getId().equals(Long.parseLong(managerId)))
                    .collect(Collectors.toList());
        }
        if (!"default".equals(expertId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getOpportunity().getUser().getId().equals(Long.parseLong(expertId)))
                    .collect(Collectors.toList());
        }
//        if (startDate != null && endDate != null) {
//            checkLists = checkLists.stream()
////                    .filter(checkList -> {
////                        LocalDate date = checkList.getWorkSchedule().getDate().toLocalDate();
////                        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
////                                (date.isEqual(endDate) || date.isBefore(endDate));
////                    })
//                    .collect(Collectors.toList());
//        }
        return checkLists.stream()
                .map(dtoBuilder::buildCheckListAnalyticsDto)
                .collect(Collectors.toList());
    }

    @Override
    public CheckListResultDto getResultByUuidLink(String uuidLink) {
        return dtoBuilder.buildCheckListResultDto(
                checkListRepository.findByUuidLinkAndStatus(uuidLink, Status.DONE)
                        .orElseThrow(() -> new NotFoundException("Check list not found"))
        );
    }

    @Override
    public ResponseEntity<?> updateCheckStatusCheckList(Long id) {
        CheckList checkList = getModelCheckListById(id);
        if(checkList.getStatus().equals(Status.DONE)) {
            throw new IllegalArgumentException("Даннный чеклист уже опубликован");
        }

        checkList.setUuidLink(String.valueOf(UUID.randomUUID()));
        checkList.setStatus(Status.DONE);
        return ResponseEntity.ok(checkListRepository.save(checkList));
    }


    @Override
    public void bindChecklistWithCriterion(CheckListMiniSupervisorCreateDto checklistDto) {
        CheckList checkList = checkListRepository.findCheckListByWorkSchedule_IdAndAndOpportunity_Id(checklistDto.getWorkScheduleId(), checklistDto.getOpportunityId());
        log.info(checkList.toString());

        for (CriteriaMaxValueDto criteriaMaxValueDto : checklistDto.getCriteriaMaxValueDtoList()) {
            CheckListsCriteria checkListsCriteria = CheckListsCriteria.builder()
                    .checklist(checkList)
                    .criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
                    .maxValue(criteriaMaxValueDto.getMaxValue())
                    .value(0)
                    .build();
            if (!Objects.equals(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()).getSection().getName(), "")) {
                checkListsCriteria.setMaxValue(1);
            }
            log.info("чеклист {} связан с критерием {}", checkList, criteriaMaxValueDto.getCriteriaId());
            checklistCriteriaRepository.save(checkListsCriteria);

            CriteriaPizzeria criteriaPizzeria = CriteriaPizzeria.builder()
                    .pizzeria(checklistDto.getPizzeria())
                    .criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
                    .build();
            criteriaPizzeriaService.save(criteriaPizzeria);

            log.info("Чек лист и все необходимые связи созданы");

        }
    }
}