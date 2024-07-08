package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dao.CheckListDao;
import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.dto.checklist.CheckListMiniSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorEditDto;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityEditDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.work_schedule.WorkScheduleSupervisorEditDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.IncorrectDateException;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.*;
import kg.attractor.xfood.service.impl.CheckListCriteriaServiceImpl;
import kg.attractor.xfood.service.impl.CheckListServiceImpl;
import kg.attractor.xfood.service.impl.DtoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CheckListServiceTest {

    @InjectMocks
    private CheckListServiceImpl checkListService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CriteriaService criteriaService;

    @Mock
    private CheckListCriteriaServiceImpl checkListCriteriaService;

    @Mock
    private CriteriaPizzeriaService criteriaPizzeriaService;


    @Mock
    private CheckListRepository checkListRepository;
    @Mock
    private WorkScheduleService workScheduleService;
    @Mock
    private OpportunityService opportunityService;

    @Mock
    private DtoBuilder dtoBuilder;

    @Mock
    private CheckListDao checkListDao;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("test@example.com")
                .password("password")
                .roles(String.valueOf(Role.EXPERT))
                .build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        ReflectionTestUtils.setField(checkListService, "checkListCriteriaService", checkListCriteriaService);

    }

    @Test
    void testUpdateCheckStatusCheckList_successfulUpdate() {
        String uuid = "2900fd68-3139-466b-ba51-04242077b67d";
        LocalTime duration = LocalTime.parse("23:30:30");
        CheckList checkList = new CheckList();

        checkList.setStatus(Status.IN_PROGRESS);
        checkList.setUuidLink(uuid);
        checkList.setDuration(duration);

        when(checkListRepository.findByUuidLink(uuid)).thenReturn(Optional.of(checkList));
        doNothing().when(checkListDao).updateStatusToDone(Status.DONE, checkList);

        checkListService.updateCheckStatusCheckList(uuid, duration);

        assertEquals(Status.DONE, checkList.getStatus());
        assertEquals(duration, checkList.getDuration());
    }

    @Test
    void testUpdateCheckStatusCheckList_alreadyDone() {
        String uuid = "2900fd68-3139-466b-ba51-04242077b67d";
        LocalTime duration = LocalTime.parse("23:30:30");
        CheckList checkList = new CheckList();
        checkList.setUuidLink(uuid);
        checkList.setStatus(Status.DONE);
        when(checkListRepository.findByUuidLink(uuid)).thenReturn(Optional.of(checkList));


        var exception = assertThrows(IllegalArgumentException.class, () -> {
            checkListService.updateCheckStatusCheckList(uuid, duration);
        });
        assertEquals("Данный чеклист уже опубликован", exception.getMessage());
    }

    @Test
    void testUpdateCheckStatusCheckList_durationNotSpecified() {
        String uuid = "2900fd68-3139-466b-ba51-04242077b67d";
        CheckList checkList = new CheckList();
        checkList.setUuidLink(uuid);
        checkList.setStatus(Status.IN_PROGRESS);
        when(checkListRepository.findByUuidLink(uuid)).thenReturn(Optional.of(checkList));

        IncorrectDateException exception = assertThrows(IncorrectDateException.class, () -> {
            checkListService.updateCheckStatusCheckList(uuid, null);
        });
        assertEquals("Введите время,затраченное на проверку чек-листа!", exception.getMessage());
    }

    @Test
    void testGetAnalyticsForExpert() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.EXPERT);

        CheckList checkList = new CheckList();
        checkList.setStatus(Status.DONE);

        List<CheckList> checkLists = Collections.singletonList(checkList);
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE))).thenReturn(checkLists);
        when(dtoBuilder.buildCheckListAnalyticsDto(any(CheckList.class))).thenReturn(checkListAnalyticsDto);

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE));
        verify(dtoBuilder, times(1)).buildCheckListAnalyticsDto(any(CheckList.class));
    }

    @Test
    void testGetAnalyticsForAdmin() {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setRole(Role.ADMIN);

        CheckList checkList = new CheckList();
        checkList.setStatus(Status.DONE);

        List<CheckList> checkLists = Collections.singletonList(checkList);
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findByStatus(eq(Status.DONE))).thenReturn(checkLists);
        when(dtoBuilder.buildCheckListAnalyticsDto(any(CheckList.class))).thenReturn(checkListAnalyticsDto);

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findByStatus(eq(Status.DONE));
        verify(dtoBuilder, times(1)).buildCheckListAnalyticsDto(any(CheckList.class));
    }

    @Test
    void testGetAnalyticsForSupervisor() {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setRole(Role.SUPERVISOR);

        CheckList checkList = new CheckList();
        checkList.setStatus(Status.DONE);

        List<CheckList> checkLists = Collections.singletonList(checkList);
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findByStatus(eq(Status.DONE))).thenReturn(checkLists);
        when(dtoBuilder.buildCheckListAnalyticsDto(any(CheckList.class))).thenReturn(checkListAnalyticsDto);

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertEquals(1, result.size());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findByStatus(eq(Status.DONE));
        verify(dtoBuilder, times(1)).buildCheckListAnalyticsDto(any(CheckList.class));
    }

    @Test
    void testGetAnalyticsEmptyResult() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.EXPERT);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE))).thenReturn(Collections.emptyList());

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE));
        verify(dtoBuilder, never()).buildCheckListAnalyticsDto(any());
    }

    @Test
    void testGetAnalyticsRevertedDates_ReturnEmptyResult() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.EXPERT);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE))).thenReturn(Collections.emptyList());

        LocalDate endDate = LocalDate.parse("2024-06-30");
        LocalDate startDate = LocalDate.parse("2024-06-15");

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", endDate, startDate);

        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE));

        verify(dtoBuilder, never()).buildCheckListAnalyticsDto(any());
    }

    @Test
    void testCheckListCreatingWithAllValidData() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.EXPERT);

        Manager manager = Manager.builder()
                .id(1L)
                .build();
        Pizzeria pizzeria = Pizzeria.builder().name("Test").build();

        WorkSchedule workSchedule = WorkSchedule.builder()
                .manager(manager)
                .startTime(LocalDateTime.parse("2024-06-17T10:00:00"))
                .endTime(LocalDateTime.parse("2024-06-17T17:00:00"))
                .pizzeria(pizzeria)
                .build();
        CheckListSupervisorCreateDto createDtoSupervisor = CheckListSupervisorCreateDto.builder()
                .checkTypeId(1L)
                .criteriaMaxValueDtoList(List.of(CriteriaMaxValueDto.builder().criteriaId(1L).maxValue(2).build()))
                .date(LocalDate.parse("2024-06-30"))
                .managerId(manager.getId())
                .expertId(user.getId())
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("17:00:00"))
                .build();

        when(workScheduleService.findWorkScheduleByManagerAndDate(manager.getId(), LocalDate.parse("2024-06-30"))).thenReturn(workSchedule);
        when(opportunityService.save(any(Opportunity.class))).thenReturn(1L);
        when(userService.findById(1L)).thenReturn(user);

        CheckListMiniSupervisorCreateDto createDto = checkListService.create(createDtoSupervisor);

        assertNotNull(createDto);
        assertEquals(1L, createDto.getOpportunityId());
        assertEquals(workSchedule.getId(), createDto.getWorkScheduleId());
        assertEquals(createDtoSupervisor.getCriteriaMaxValueDtoList(), createDto.getCriteriaMaxValueDtoList());
    }

    @Test
    void testCheckListCreatingWithIncorrectExpertDate() {
        IncorrectDateException thrown = Assertions.assertThrows(IncorrectDateException.class, () -> {
            User user = new User();
            user.setId(1L);
            user.setRole(Role.EXPERT);

            Manager manager = Manager.builder().id(1L).build();
            Pizzeria pizzeria = Pizzeria.builder().name("Test").build();

            WorkSchedule workSchedule = WorkSchedule.builder()
                    .manager(manager)
                    .startTime(LocalDateTime.parse("2024-06-17T10:00:00"))
                    .endTime(LocalDateTime.parse("2024-06-17T17:00:00"))
                    .pizzeria(pizzeria)
                    .build();
            CheckListSupervisorCreateDto createDtoSupervisor = CheckListSupervisorCreateDto.builder()
                    .checkTypeId(1L)
                    .criteriaMaxValueDtoList(List.of(CriteriaMaxValueDto.builder().criteriaId(1L).maxValue(2).build()))
                    .date(LocalDate.parse("2024-06-30"))
                    .managerId(manager.getId())
                    .expertId(user.getId())
                    .startTime(LocalTime.parse("17:00:00"))
                    .endTime(LocalTime.parse("10:00:00"))
                    .build();

            when(workScheduleService.findWorkScheduleByManagerAndDate(manager.getId(), LocalDate.parse("2024-06-30"))).thenReturn(workSchedule);
            when(opportunityService.save(any(Opportunity.class))).thenReturn(1L);
            when(userService.findById(1L)).thenReturn(user);

            checkListService.create(createDtoSupervisor);
        });
        Assertions.assertEquals("Время начала не может быть позже времени конца смены", thrown.getMessage());

    }

    @Test
    void testCheckListCreatingWithEmptyCriterion() {
        IncorrectDateException thrown = Assertions.assertThrows(IncorrectDateException.class, () -> {
            User user = new User();
            user.setId(1L);
            user.setRole(Role.EXPERT);

            Manager manager = Manager.builder().id(1L).build();
            Pizzeria pizzeria = Pizzeria.builder().name("Test").build();

            WorkSchedule workSchedule = WorkSchedule.builder()
                    .manager(manager)
                    .startTime(LocalDateTime.parse("2024-06-17T10:00:00"))
                    .endTime(LocalDateTime.parse("2024-06-17T17:00:00"))
                    .pizzeria(pizzeria)
                    .build();
            CheckListSupervisorCreateDto createDtoSupervisor = CheckListSupervisorCreateDto.builder()
                    .checkTypeId(1L)
                    .criteriaMaxValueDtoList(Collections.emptyList())
                    .date(LocalDate.parse("2024-06-30"))
                    .managerId(manager.getId())
                    .expertId(user.getId())
                    .startTime(LocalTime.parse("10:00:00"))
                    .endTime(LocalTime.parse("17:00:00"))
                    .build();

            when(workScheduleService.findWorkScheduleByManagerAndDate(manager.getId(), LocalDate.parse("2024-06-30"))).thenReturn(workSchedule);
            when(opportunityService.save(any(Opportunity.class))).thenReturn(1L);
            when(userService.findById(1L)).thenReturn(user);

            checkListService.create(createDtoSupervisor);
        });

        Assertions.assertEquals("Чек лист не содержит критериев", thrown.getMessage());

    }

    @Test
    void testCheckListCreatingWithIncorrectManagerDate() {
        IncorrectDateException thrown = Assertions.assertThrows(IncorrectDateException.class, () -> {
            User user = new User();
            user.setId(1L);
            user.setRole(Role.EXPERT);

            Manager manager = Manager.builder().id(1L).build();
            Pizzeria pizzeria = Pizzeria.builder().name("Test").build();

            WorkSchedule workSchedule = WorkSchedule.builder()
                    .manager(manager)
                    .startTime(LocalDateTime.parse("2024-06-17T18:00:00"))
                    .endTime(LocalDateTime.parse("2024-06-17T20:00:00"))
                    .pizzeria(pizzeria)
                    .build();
            CheckListSupervisorCreateDto createDtoSupervisor = CheckListSupervisorCreateDto.builder()
                    .checkTypeId(1L)
                    .criteriaMaxValueDtoList(List.of(CriteriaMaxValueDto.builder().criteriaId(1L).maxValue(2).build()))
                    .date(LocalDate.parse("2024-06-30"))
                    .managerId(manager.getId())
                    .expertId(user.getId())
                    .startTime(LocalTime.parse("10:00:00"))
                    .endTime(LocalTime.parse("17:00:00"))
                    .build();

            when(workScheduleService.findWorkScheduleByManagerAndDate(manager.getId(), LocalDate.parse("2024-06-30"))).thenReturn(workSchedule);
            when(opportunityService.save(any(Opportunity.class))).thenReturn(1L);
            when(userService.findById(1L)).thenReturn(user);

            checkListService.create(createDtoSupervisor);
        });
        Assertions.assertEquals("Время начала смены менеджера не может быть позже времени окончания работы эксперта", thrown.getMessage());

    }


    @Test
    void testGetChecklistByUuid() {
        String uuid = "2900fd68-3139-466b-ba51-04242077b67d";
        User user = new User();
        user.setId(1L);
        user.setRole(Role.EXPERT);

        Manager manager = Manager.builder().id(1L).build();
        Pizzeria pizzeria = Pizzeria.builder().name("Test Pizzeria").build();
        CheckList checkList = CheckList.builder()
                .id(1L)
                .workSchedule(WorkSchedule.builder()
                        .id(1L)
                        .startTime(LocalDateTime.parse("2024-06-17T10:00:00"))
                        .endTime(LocalDateTime.parse("2024-06-17T17:00:00"))
                        .manager(manager)
                        .pizzeria(pizzeria)
                        .build())
                .opportunity(Opportunity.builder().id(1L)
                        .date(LocalDate.parse("2024-06-30"))
//                        .startTime(LocalTime.parse("10:00:00"))
//                        .endTime(LocalTime.parse("17:00:00"))
                        .user(user)
                        .build())
                .uuidLink(uuid)
                .build();

        WorkScheduleSupervisorEditDto workScheduleSupervisorEditDto = WorkScheduleSupervisorEditDto.builder()
                .id(1L)
//                .startTime(LocalDateTime.parse("2024-06-17T10:00:00"))
//                .endTime(LocalDateTime.parse("2024-06-17T17:00:00"))
                .pizzeria(PizzeriaDto.builder().name(pizzeria.getName()).build())
                .build();

        List<CheckListsCriteria> checkListsCriteria = List.of(CheckListsCriteria.builder()
                .id(1L)
                .checklist(checkList)
                .criteria(Criteria.builder()
                        .id(1L)
                        .description("Description")
                        .zone(Zone.builder().id(1L).name("Zone").build())
                        .section(Section.builder().id(1L).name("Section").build())
                        .build())
                .build());

        CheckListSupervisorEditDto expectedDto = CheckListSupervisorEditDto.builder()
                .id(uuid)
                .opportunity(OpportunityEditDto.builder()
                        .id(1L)
//                        .startTime(LocalTime.parse("10:00"))
//                        .endTime(LocalTime.parse("17:00"))
                        .date(LocalDate.parse("2024-06-30"))
                        .build())
                .workSchedule(WorkScheduleSupervisorEditDto.builder()
                        .id(1L)
//                        .startTime(LocalDateTime.parse("2024-06-17T10:00:00"))
//                        .endTime(LocalDateTime.parse("2024-06-17T17:00:00"))
                        .pizzeria(PizzeriaDto.builder().name(pizzeria.getName()).build())
                        .build())
                .criterion(Collections.emptyList())
                .build();

        when(checkListRepository.findByUuidLink(uuid)).thenReturn(Optional.of(checkList));
        when(checkListCriteriaService.findAllByChecklistId(1L)).thenReturn(checkListsCriteria);
        when(dtoBuilder.buildExpertShowDto(user)).thenReturn(new ExpertShowDto());
        when(dtoBuilder.buildPizzeriaDto(any(Pizzeria.class))).thenReturn(workScheduleSupervisorEditDto.getPizzeria());
        when(dtoBuilder.buildManagerShowDto(any(Manager.class))).thenReturn(workScheduleSupervisorEditDto.getManager());

        CheckListSupervisorEditDto dto = checkListService.getChecklistByUuid(uuid);

        assertNotNull(dto);
        assertEquals(expectedDto.getId(), dto.getId());
        assertEquals(expectedDto.getOpportunity().getId(), dto.getOpportunity().getId());
        assertEquals(expectedDto.getOpportunity().getStartTime(), dto.getOpportunity().getStartTime());
        assertEquals(expectedDto.getOpportunity().getEndTime(), dto.getOpportunity().getEndTime());
        assertEquals(expectedDto.getOpportunity().getDate(), dto.getOpportunity().getDate());
        assertEquals(expectedDto.getWorkSchedule().getId(), dto.getWorkSchedule().getId());
        assertEquals(expectedDto.getWorkSchedule().getStartTime(), dto.getWorkSchedule().getStartTime());
        assertEquals(expectedDto.getWorkSchedule().getEndTime(), dto.getWorkSchedule().getEndTime());
        assertEquals(expectedDto.getWorkSchedule().getPizzeria().getName(), dto.getWorkSchedule().getPizzeria().getName());
    }

    @Test
    void testBindChecklistWithCriterion() {
        String uuid = "2900fd68-3139-466b-ba51-04242077b67d";
        CheckListMiniSupervisorCreateDto checklistDto = CheckListMiniSupervisorCreateDto.builder()
                .workScheduleId(1L)
                .opportunityId(1L)
                .criteriaMaxValueDtoList(List.of(CriteriaMaxValueDto.builder().maxValue(3).criteriaId(1L).build()))
                .pizzeria(Pizzeria.builder().build())
                .build();

        CheckList checkList = CheckList.builder()
                .uuidLink(uuid)
                .id(1L)
                .build();

        Criteria criteria = Criteria.builder().id(1L).section(Section.builder().build()).build();

        when(checkListRepository.findCheckListByWorkSchedule_IdAndAndOpportunity_Id(1L, 1L)).thenReturn(checkList);
        when(criteriaService.findById(1L)).thenReturn(criteria);

        checkListService.bindChecklistWithCriterion(checklistDto);

        verify(checkListRepository).save(checkList);
        verify(checkListCriteriaService, times(1)).save(any(CheckListsCriteria.class));
        verify(criteriaPizzeriaService, times(1)).save(any(CriteriaPizzeria.class));
    }
}


