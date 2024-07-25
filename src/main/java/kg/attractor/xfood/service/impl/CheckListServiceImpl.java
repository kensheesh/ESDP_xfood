package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dao.CheckListDao;
import kg.attractor.xfood.dto.LocationDto;
import kg.attractor.xfood.dto.checklist.*;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.statistics.CellDto;
import kg.attractor.xfood.dto.statistics.RowDto;
import kg.attractor.xfood.dto.statistics.StatisticsDto;
import kg.attractor.xfood.dto.statistics.TableDto;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
    private final WorkScheduleService workScheduleService;
    private CheckListCriteriaServiceImpl checkListCriteriaService;
    private final UserService userService;
    private ManagerService managerService;
    private final CriteriaService criteriaService;
    private final CheckListRepository checkListRepository;
    private final ChecklistCriteriaRepository checklistCriteriaRepository;
    private final UserRepository userRepository;
    private final CriteriaTypeService criteriaTypeService;
    private final PizzeriaService pizzeriaService;
    private final CheckListDao checkListDao;

    private final DtoBuilder dtoBuilder;

    private static final String DEFAULT = "default";
    private final CheckTypeService checkTypeService;

    @Autowired
    public void setCheckListCriteriaService(@Lazy CheckListCriteriaServiceImpl checkListCriteriaService) {
        this.checkListCriteriaService = checkListCriteriaService;
    }

    @Autowired
    public void setManagerService(@Lazy ManagerService managerService) {
        this.managerService = managerService;
    }

    @Override
    public List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status) {
        return null;
//        return checkListRepository.findCheckListByExpertEmailAndStatus(username, status)
//                .stream()
//                .map(dtoBuilder::buildChecklistDto)
//                .toList();
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
        if (checkListRepository.existsByWorkSchedule_IdAndExpert_Id(workSchedule.getId(), createDto.getExpertId())){
            throw new IncorrectDateException("Проверка на "+createDto.getStartTime()+" - "+createDto.getEndTime()+" и эксперта c айди "+createDto.getExpertId()+"уже создана");
        }

        log.info(workSchedule.getStartTime().toString());
        log.info(createDto.getEndTime().toString());
        if (createDto.getEndTime().isBefore(workSchedule.getStartTime().toLocalTime())) {
            throw new IncorrectDateException("Время начала смены менеджера не может быть позже времени окончания работы эксперта");
        }

        createDto.getCriteriaMaxValueDtoList().removeIf(criteriaMaxValueDto -> criteriaMaxValueDto.getCriteriaId() == null);
        createDto.getCriteriaMaxValueDtoList().sort(Comparator.comparing(CriteriaMaxValueDto::getCriteriaId));

        String uuid = UUID.randomUUID().toString();
        checkListRepository.saveCheckList(workSchedule.getId(), Status.NEW.getStatus(), createDto.getExpertId(), uuid);
        checkListRepository.flush();
        return CheckListMiniSupervisorCreateDto.builder().checkTypeId(createDto.getCheckTypeId()).expertId(createDto.getExpertId()).workScheduleId(workSchedule.getId()).criteriaMaxValueDtoList(createDto.getCriteriaMaxValueDtoList()).pizzeria(workSchedule.getPizzeria()).build();
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
        return null;
        //
//        return checkListRepository.findCheckListByStatus(status)
//                .stream()
//                .map(dtoBuilder::buildChecklistDto)
//                .toList();
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
        User user = userRepository.findByEmail(AuthParams.getPrincipal().getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<CheckList> checkLists = getCheckListsByUserRole(user);

        checkLists = filterByPizzeriaId(checkLists, pizzeriaId);
        checkLists = filterByManagerId(checkLists, managerId);
        checkLists = filterByExpertId(checkLists, expertId);
        checkLists = filterByDateRange(checkLists, startDate, endDate);

        return checkLists.stream()
                .map(dtoBuilder::buildCheckListAnalyticsDto)
                .toList();
    }

    private List<CheckList> getCheckListsByUserRole(User user) {
        if (user.getRole().equals(Role.EXPERT)) {
            return checkListRepository.findCheckListByExpertEmailAndStatus(user.getEmail(), Status.DONE);
        } else {
            return checkListRepository.findByStatus(Status.DONE);
        }
    }

    private List<CheckList> filterByPizzeriaId(List<CheckList> checkLists, String pizzeriaId) {
        if (!DEFAULT.equals(pizzeriaId)) {
            return checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getPizzeria().getId().equals(Long.parseLong(pizzeriaId)))
                    .toList();
        }
        return checkLists;
    }

    private List<CheckList> filterByManagerId(List<CheckList> checkLists, String managerId) {
        if (!DEFAULT.equals(managerId)) {
            return checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getManager().getId().equals(Long.parseLong(managerId)))
                    .toList();
        }
        return checkLists;
    }

    private List<CheckList> filterByExpertId(List<CheckList> checkLists, String expertId) {
        if (!DEFAULT.equals(expertId)) {
            return checkLists.stream()
                    .filter(checkList -> checkList.getExpert().getId().equals(Long.parseLong(expertId)))
                    .toList();
        }
        return checkLists;
    }

    private List<CheckList> filterByDateRange(List<CheckList> checkLists, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return checkLists.stream()
                    .filter(checkList -> {
                        LocalDate startTime = checkList.getWorkSchedule().getStartTime().toLocalDate();
                        LocalDate endTime = checkList.getWorkSchedule().getEndTime().toLocalDate();
                        return (startTime.isEqual(startDate) || startTime.isAfter(startDate)) &&
                                (endTime.isEqual(endDate) || endTime.isBefore(endDate));
                    })
                    .toList();
        }
        return checkLists;
    }

    @Override
    public CheckListResultDto getResultByUuidLink(String uuidLink) {
        return dtoBuilder.buildCheckListResultDto(
                checkListRepository.findByUuidLinkAndStatus(uuidLink, Status.DONE)
                        .orElseThrow(() -> new NotFoundException("Check list not found"))
        );
    }

    @Override
    public CheckList updateCheckStatusCheckList(String id, LocalTime duration) {
        CheckList checkList = getModelCheckListById(id);
        if (checkList.getStatus().equals(Status.DONE)) {
            throw new IllegalArgumentException("Данный чеклист уже опубликован");
        }

        checkList.setStatus(Status.DONE);
        checkList.setEndTime(LocalDateTime.now());
        if (duration == null) {
            throw new IncorrectDateException("Введите время,затраченное на проверку чек-листа!");
        }
        checkList.setDuration(duration);
        log.info(duration + "Duration for checklist with id " + checkList.getId());
        checkListDao.updateStatusToDone(Status.DONE, checkList);
        return checkList;
    }

    @Override
    public CheckListSupervisorEditDto getChecklistByUuid(String uuid) {
        CheckList checkList = checkListRepository.findByUuidLink(uuid).orElseThrow(() -> new NotFoundException("Check list not found by uuid: " + uuid));
        ExpertShowDto expert = dtoBuilder.buildExpertShowDto(checkList.getExpert());

        WorkScheduleSupervisorEditDto workScheduleDto = WorkScheduleSupervisorEditDto.builder()
                .id(checkList.getWorkSchedule().getId())
                .startTime(checkList.getWorkSchedule().getStartTime())
                .endTime(checkList.getWorkSchedule().getEndTime())
                .pizzeria(dtoBuilder.buildPizzeriaDto(checkList.getWorkSchedule().getPizzeria()))
                .manager(dtoBuilder.buildManagerShowDto(checkList.getWorkSchedule().getManager()))
                .build();

        List<CheckListsCriteria> checkListsCriteria = checkListCriteriaService.findAllByChecklistId(checkList.getId());
        List<CriteriaExpertShowDto> criterionWithMaxValue = new ArrayList<>();
        int sum = 0 ;
        for (CheckListsCriteria criteria : checkListsCriteria) {
            sum +=criteria.getMaxValue();
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
                .workSchedule(workScheduleDto)
                .expert(expert)
                .totalValue(sum)
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
        Manager manager = managerService.findByPhoneNumber(checkListDto.getWorkSchedule().getManager().getPhoneNumber());
        if (checkListDto.getWorkSchedule().getStartTime().isAfter(checkListDto.getWorkSchedule().getEndTime())) {
            throw new IncorrectDateException("Время начала смены менеджера не может быть позже времени конца смены");
        }
        WorkSchedule workSchedule = WorkSchedule.builder()
                .id(checkList.getWorkSchedule().getId())
                .manager(manager)
                .startTime(checkListDto.getWorkSchedule().getStartTime())
                .endTime(checkListDto.getWorkSchedule().getEndTime())
                .pizzeria(checkList.getWorkSchedule().getPizzeria())
                .build();
        workScheduleService.save(workSchedule);
        log.info("list {}",checkListDto.getCriterion().toString());
        checkListDto.getCriterion().removeIf(criteria -> criteria.getId()==null);
        checkListCriteriaService.deleteCriterionByChecklist(checkList.getId());
        for (CriteriaExpertShowDto criteriaMaxValueDto : checkListDto.getCriterion()) {
            CheckListsCriteria checkListsCriteria = CheckListsCriteria.builder()
                    .checklist(checkList)
                    .criteria(criteriaService.findById(criteriaMaxValueDto.getId()))
                    .maxValue(criteriaMaxValueDto.getMaxValue())
                    .value(0)
                    .build();
            checkListCriteriaService.save(checkListsCriteria);
        }
        checkList.setWorkSchedule(workSchedule);
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
    public Integer getPercentageById(Long id) {
        List<CheckListsCriteria> criteriaList = checklistCriteriaRepository.findCriteriaByCheckListId(id);
        Double normalMaxSum = criteriaList.stream()
                .filter(criteria -> !criteria.getCriteria().getSection().getName().equalsIgnoreCase("WOW фактор"))
                .mapToDouble(criteria -> criteria.getMaxValue() != null ? criteria.getMaxValue() : 0.0)
                .sum();

        Double normalValue = criteriaList.stream()
                .filter(criteria -> !criteria.getCriteria().getSection().getName().equalsIgnoreCase("WOW фактор"))
                .mapToDouble(CheckListsCriteria::getValue)
                .sum();

        Double wowValue = criteriaList.stream()
                .filter(criteria -> criteria.getCriteria().getSection().getName().equalsIgnoreCase("WOW фактор"))
                .mapToDouble(CheckListsCriteria::getValue)
                .sum();

        Double percentage = (normalValue / normalMaxSum) * 100;

        if (percentage < 100) {
            Double totalValue = normalValue + wowValue;
            percentage = (totalValue / normalMaxSum) * 100;

            if (percentage > 100) {
                percentage = 100.0;
            }
        }
        return (int) Math.ceil(percentage);
    }

    @Override
    public StatisticsDto getStatistics(LocalDate from, LocalDate to) {
        StatisticsDto statisticsDto = new StatisticsDto();
        List<CheckList> checkLists = checkListRepository.findAll();
        List<PizzeriaDto> pizzerias = pizzeriaService.getAllPizzerias();
        log.info("all checklists {}", checkLists);
        checkLists.removeIf(checkList -> checkList.getEndTime()==null&& !Objects.equals(checkList.getStatus().getStatus(), "DONE"));
        List<RowDto> rowDtos = new ArrayList<>();
        log.info("after removing checklists {}", checkLists);
        for (CheckList checkList : checkLists) {
            Manager manager = checkList.getWorkSchedule().getManager();
            Pizzeria pizzeria = checkList.getWorkSchedule().getPizzeria();
            User expert = checkList.getExpert();
            Integer points = checkListCriteriaService.findAllByChecklistId(checkList.getId())
                    .stream().mapToInt(CheckListsCriteria::getValue).sum();
            CellDto cellDto = CellDto.builder()
                    .date(checkList.getEndTime().toLocalDate())
                    .points(points)
                    .build();

            boolean found = false;
            for (RowDto rowDto : rowDtos) {
                if (rowDto.getPizzeria().equals(pizzeria) && rowDto.getExpert().equals(expert) && rowDto.getManager().equals(manager)) {
                    rowDto.getCells().add(cellDto);
                    found = true;
                    break;
                }
            }
            if (!found) {
                rowDtos.add(RowDto.builder()
                        .manager(ManagerDto.builder()
                                .name(manager.getName())
                                .surname(manager.getSurname())
                                .id(manager.getId())
                                .phoneNumber(manager.getPhoneNumber())
                                .build())
                        .expert(ExpertShowDto.builder()
                                .name(expert.getName())
                                .surname(expert.getSurname())
                                .id(expert.getId())
                                .build())
                        .pizzeria(PizzeriaDto.builder()
                                .name(pizzeria.getName())
                                .location(LocationDto.builder().countryCode(pizzeria.getLocation().getName()).build())
                                .uuid(pizzeria.getUuid())
                                .build())
                        .cells(List.of(cellDto))
                        .build());
            }
        }
        for (RowDto rowDto : rowDtos) {
            int sum = 0;
            for (CellDto cellDto : rowDto.getCells()) {
                sum += cellDto.getPoints();
            }
            rowDto.setAverageByRow(sum / rowDto.getCells().size());
        }
        log.info("rows {}", rowDtos);
        List<TableDto> tableDtos = new ArrayList<>();
        for (PizzeriaDto pizzeriaDto: pizzerias){
            List<RowDto> rowsByPizzeria = new ArrayList<>();
            for (RowDto rowDto : rowDtos) {
                if (pizzeriaDto.getName().equals(rowDto.getPizzeria().getName())) {
                    rowsByPizzeria.add(rowDto);
                }
            }
            tableDtos.add(TableDto.builder()
                            .rows(rowsByPizzeria)
                    .build());
        }
        log.info("tables {}", tableDtos);
        for(TableDto tableDto : tableDtos){
            int sum = 0;
            for (RowDto rowDto: tableDto.getRows()) {
                sum +=rowDto.getAverageByRow();
            }
            tableDto.setAverageByTable(sum / tableDto.getRows().size());
        }
        statisticsDto.setTables(tableDtos);
        int sum = 0;
        for (TableDto tableDto : tableDtos) {
           sum = tableDto.getAverageByTable();
        }
        statisticsDto.setAverage(sum / tableDtos.size());
        return statisticsDto;
    }



    @Override
    public void bindChecklistWithCriterion(CheckListMiniSupervisorCreateDto checklistDto) {
        CheckList checkList = checkListRepository.findCheckListByWorkSchedule_IdAndExpert_Id(checklistDto.getWorkScheduleId(), checklistDto.getExpertId()).orElseThrow(()-> new NoSuchElementException("Чек-лист не найден "));
        log.info(checkList.toString());
        CheckType checkType = checkTypeService.getById(checklistDto.getCheckTypeId());
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
            CriteriaType criteriaType = CriteriaType.builder()
                    .criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
                    .type(checkType)
                    .build();
            log.info("критерия {} связана с типом {}", criteriaMaxValueDto.getCriteriaId(), criteriaType.getType());
            criteriaTypeService.save(criteriaType);
            log.info("Чек лист и все необходимые связи созданы");

        }
    }

}
