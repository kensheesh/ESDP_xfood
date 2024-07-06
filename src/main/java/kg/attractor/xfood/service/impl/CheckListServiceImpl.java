package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dao.CheckListDao;
import kg.attractor.xfood.dto.checklist.*;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityEditDto;
import kg.attractor.xfood.dto.work_schedule.WorkScheduleSupervisorEditDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.IncorrectDateException;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final CriteriaService criteriaService;
    private final CriteriaPizzeriaService criteriaPizzeriaService;
    private final OpportunityService opportunityService;
    private final CheckListRepository checkListRepository;
    // private final CheckListCriteriaService checkListCriteriaService;
    private final ManagerService managerService;
    private CheckListCriteriaServiceImpl checkListCriteriaService;
    private final ChecklistCriteriaRepository checklistCriteriaRepository;
    private final CheckListDao checkListDao;

    @Autowired
    public void setCheckListCriteriaService(@Lazy CheckListCriteriaServiceImpl checkListCriteriaService) {
        this.checkListCriteriaService = checkListCriteriaService;
    }

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
        WorkSchedule workSchedule = workScheduleService.findWorkScheduleByManagerAndDate(createDto.getManagerId(), createDto.getDate());
        log.info(workSchedule.getStartTime().toString());
        log.info(createDto.getEndTime().toString());
        if (createDto.getEndTime().isBefore(workSchedule.getStartTime().toLocalTime())) {
            throw new IncorrectDateException("Время начала смены менеджера не может быть позже времени окончания работы эксперта");
        }
        //TODO уточнить надо ли делать проверку по work_schedule, opportunity and type и если необходимо добавить
        createDto.getCriteriaMaxValueDtoList().removeIf(criteriaMaxValueDto -> criteriaMaxValueDto.getCriteriaId() == null);
        createDto.getCriteriaMaxValueDtoList().sort(Comparator.comparing(CriteriaMaxValueDto::getCriteriaId));

        LocalDate date;
        if (workSchedule.getStartTime().toLocalDate().isBefore(workSchedule.getEndTime().toLocalDate())) {
            date = workSchedule.getEndTime().toLocalDate();
        } else {
            date = workSchedule.getEndTime().toLocalDate();
        }
        Opportunity opportunity = Opportunity.builder()
                .user(userService.findById(createDto.getExpertId()))
                .date(date)
                .startTime(createDto.getStartTime())
                .endTime(createDto.getEndTime())
                .build();

        Long id = opportunityService.save(opportunity);
        checkListRepository.saveChecklist(id, workSchedule.getId(), Status.NEW.getStatus());
        checkListRepository.flush();
        return CheckListMiniSupervisorCreateDto.builder().opportunityId(id).workScheduleId(workSchedule.getId()).criteriaMaxValueDtoList(createDto.getCriteriaMaxValueDtoList()).pizzeria(workSchedule.getPizzeria()).build();
    }

    @Override
    public ChecklistShowDto getCheckListById(String id) {
        CheckList checkList = getModelCheckListById(id);
        return dtoBuilder.buildChecklistShowDto(checkList);
    }

    @Override
    public CheckList getModelCheckListById(String id) {
        return checkListRepository.findByUuidLink(id).orElseThrow(() -> new NoSuchElementException("Can't find checklist by uuid " + id));
    }

    @Override
    public CheckList getModelCheckListById(Long id) {
        return checkListRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find checklist by ID " + id));
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
    public CheckListResultDto getResult(String checkListId) {
        CheckList checkList = getModelCheckListById(checkListId);

        if (checkList.getStatus().equals(Status.DONE) || checkList.getStatus().equals(Status.IN_PROGRESS)) {
            return dtoBuilder.buildCheckListResultDto(
                    checkListRepository.findByIdAndStatus(checkListId, checkList.getStatus())
                            .orElseThrow(() -> new NotFoundException("Check list not found"))
            );
        }

        throw new IllegalArgumentException("По данной проверке нет еще результатов");
    }

    @Override
    public CheckListResultDto getResult(Long checkListId) {
        CheckList checkList = getModelCheckListById(checkListId);

        if (checkList.getStatus().equals(Status.DONE) || checkList.getStatus().equals(Status.IN_PROGRESS)) {
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
        if (startDate != null && endDate != null) {
            checkLists = checkLists.stream()
                    .filter(checkList -> {
                        LocalDate startTime = checkList.getWorkSchedule().getStartTime().toLocalDate();
                        LocalDate endTime = checkList.getWorkSchedule().getEndTime().toLocalDate();
                        return (startTime.isEqual(startDate) || startTime.isAfter(startDate)) &&
                                (endTime.isEqual(endDate) || endTime.isBefore(endDate));
                    })
                    .collect(Collectors.toList());
        }
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
    public void updateCheckStatusCheckList(String id, LocalTime duration) {
        CheckList checkList = getModelCheckListById(id);
        if (checkList.getStatus().equals(Status.DONE)) {
            throw new IllegalArgumentException("Даннный чеклист уже опубликован");
        }

        checkList.setStatus(Status.DONE);
        if (duration == null) {
            throw new IncorrectDateException("Введите время,затраченное на проверку чек-листа!");
        }
        checkList.setDuration(duration);
        log.info(duration + "Duration for checklist with id " + checkList.getId());
        checkListDao.updateStatusToDone(Status.DONE, checkList);
    }

    @Override
    public CheckListSupervisorEditDto getChecklistByUuid(String uuid) {
        CheckList checkList = checkListRepository.findByUuidLink(uuid).orElseThrow(() -> new NotFoundException("Check list not found by uuid: " + uuid));
        ExpertShowDto expert = dtoBuilder.buildExpertShowDto(checkList.getOpportunity().getUser());

        OpportunityEditDto opportunityEditDto = OpportunityEditDto.builder()
                .id(checkList.getOpportunity().getId())
                .date(checkList.getOpportunity().getDate())
                .startTime(checkList.getOpportunity().getStartTime())
                .endTime(checkList.getOpportunity().getEndTime())
                .user(expert)
                .build();

        WorkScheduleSupervisorEditDto workScheduleDto = WorkScheduleSupervisorEditDto.builder()
                .id(checkList.getWorkSchedule().getId())
                .startTime(checkList.getWorkSchedule().getStartTime())
                .endTime(checkList.getWorkSchedule().getEndTime())
                .pizzeria(dtoBuilder.buildPizzeriaDto(checkList.getWorkSchedule().getPizzeria()))
                .manager(dtoBuilder.buildManagerShowDto(checkList.getWorkSchedule().getManager()))
                .build();

        List<CheckListsCriteria> checkListsCriteria = checkListCriteriaService.findAllByChecklistId(checkList.getId());
        List<CriteriaExpertShowDto> criterionWithMaxValue = new ArrayList<>();
        for (CheckListsCriteria criteria : checkListsCriteria) {
            criterionWithMaxValue.add(CriteriaExpertShowDto.builder()
                    .id(criteria.getCriteria().getId())
                    .maxValue(criteria.getMaxValue())
                    .description(criteria.getCriteria().getDescription())
                    .zone(criteria.getCriteria().getZone().getName())
                    .section(criteria.getCriteria().getSection().getName())
                    .build());
        }
        criterionWithMaxValue.removeIf(criteria -> !criteria.getSection().equals(""));
        return CheckListSupervisorEditDto.builder()
                .id(checkList.getUuidLink())
                .opportunity(opportunityEditDto)
                .workSchedule(workScheduleDto)
                .criterion(criterionWithMaxValue.stream()
                        .sorted(Comparator.comparing(CriteriaExpertShowDto::getSection)
                                .thenComparing(CriteriaExpertShowDto::getZone)).toList())
                .build();
    }

    @Transactional
    @Override
    public void edit(CheckListSupervisorEditDto checkListDto) {
        log.info(checkListDto.toString());
        CheckList checkList = checkListRepository.findByUuidLink(checkListDto.getId()).orElseThrow(() -> new NotFoundException("Check list not found by uuid: " + checkListDto.getId()));
        Manager manager = managerService.findById(checkListDto.getWorkSchedule().getManager().getId());
        if (checkListDto.getWorkSchedule().getStartTime().isAfter(checkListDto.getWorkSchedule().getEndTime())) {
            throw new IncorrectDateException("Время начала смены менеджера не может быть позже времени конца смены");
        }
        if (checkListDto.getOpportunity().getStartTime().isAfter(checkListDto.getOpportunity().getEndTime())) {
            throw new IncorrectDateException("Время начала смены эксперта не может быть позже времени конца смены");
        }
        if (checkListDto.getOpportunity().getDate().atTime(checkListDto.getOpportunity().getEndTime()).isBefore(checkListDto.getWorkSchedule().getEndTime())) {
            throw new IncorrectDateException("Время конца смены эксперта не может быть раньше времени конца смены менеджера");
        }
        if (checkListDto.getOpportunity().getDate().atTime(checkListDto.getOpportunity().getEndTime()).isBefore(checkListDto.getWorkSchedule().getStartTime())) {
            throw new IncorrectDateException("Время конца смены эксперта не может быть раньше времени начала смены менеджера");
        }

        WorkSchedule workSchedule = WorkSchedule.builder()
                .id(checkList.getWorkSchedule().getId())
                .manager(manager)
                .startTime(checkListDto.getWorkSchedule().getStartTime())
                .endTime(checkListDto.getWorkSchedule().getEndTime())
                .pizzeria(checkList.getWorkSchedule().getPizzeria())
                .build();
        workScheduleService.save(workSchedule);
        Opportunity opportunity = Opportunity.builder()
                .id(checkList.getOpportunity().getId())
                .date(checkListDto.getOpportunity().getDate())
                .startTime(checkListDto.getOpportunity().getStartTime())
                .endTime(checkListDto.getOpportunity().getEndTime())
                .user(userService.findById(checkListDto.getOpportunity().getUser().getId()))
                .build();
        opportunityService.save(opportunity);
        List<CheckListsCriteria> checkListsCriteria = checkListCriteriaService.findAllByChecklistId(checkList.getId());
        for (CheckListsCriteria checkListCriteria : checkListsCriteria) {
            for (CriteriaExpertShowDto criterionDto : checkListDto.getCriterion()) {
                if (checkListCriteria.getCriteria().getId().equals(criterionDto.getId())) {

                    checkListCriteria.setMaxValue(criterionDto.getMaxValue());
                    log.info(checkListCriteria.getMaxValue().toString());
                    checkListCriteriaService.save(checkListCriteria);
                    break;
                }
            }
        }


        checkList.setWorkSchedule(workSchedule);
        checkList.setOpportunity(opportunity);
        checkListRepository.save(checkList);
    }

    @Override
    public Integer getMaxPoints(Long id) {
        List<CheckListsCriteria> criteriaList = checklistCriteriaRepository.findCriteriaByCheckListId(id);
        return (int) criteriaList.stream()
                .mapToDouble(criteria -> criteria.getMaxValue() != null ? criteria.getMaxValue() : 0.0)
                .sum();

    }


    @Override
    public void bindChecklistWithCriterion(CheckListMiniSupervisorCreateDto checklistDto) {
        CheckList checkList = checkListRepository.findCheckListByWorkSchedule_IdAndAndOpportunity_Id(checklistDto.getWorkScheduleId(), checklistDto.getOpportunityId());
        log.info(checkList.toString());
        String uuid = UUID.randomUUID().toString();
        checkList.setUuidLink(uuid);
        checkListRepository.save(checkList);
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
            checkListCriteriaService.save(checkListsCriteria);

            CriteriaPizzeria criteriaPizzeria = CriteriaPizzeria.builder()
                    .pizzeria(checklistDto.getPizzeria())
                    .criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
                    .build();
            criteriaPizzeriaService.save(criteriaPizzeria);

            log.info("Чек лист и все необходимые связи созданы");

        }
    }
}