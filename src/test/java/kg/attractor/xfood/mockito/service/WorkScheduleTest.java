package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.WorkScheduleSupervisorShowDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.workSchedule.WeekDto;
import kg.attractor.xfood.dto.workSchedule.WeeklyScheduleShowDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.model.WorkSchedule;
import kg.attractor.xfood.repository.WorkScheduleRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.WorkScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WorkScheduleTest {
    @InjectMocks
    private WorkScheduleServiceImpl workScheduleService;
    @Mock
    private WorkScheduleRepository workScheduleRepository;

    @Mock
    private DtoBuilder dtoBuilder;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
        void testGetWeekInfo_week0() {
            long week = 0L;
            LocalDateTime mockCurrentTime = LocalDateTime.of(2024, 7, 17, 16, 20);
            LocalDateTime monday = mockCurrentTime.with(DayOfWeek.MONDAY);
            LocalDateTime chosenMonday = monday.plusDays(7 * week);
            LocalDateTime sunday = chosenMonday.plusDays(6);

            try (MockedStatic<LocalDateTime> mocked = Mockito.mockStatic(LocalDateTime.class)) {
                mocked.when(LocalDateTime::now).thenReturn(mockCurrentTime);

                WeekDto expectation = WeekDto.builder()
                        .weekOrder(week)
                        .monday(chosenMonday.toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .sunday(sunday.toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .build();

                WeekDto result = workScheduleService.getWeekInfo(week);

                assertNotNull(result);
                assertEquals(expectation, result);
            }
        }

    @Test
    void testGetWeeklySchedulesByPizzeriaId() {
        long pizzeriaId = 1L;
        long week = 0L;
        LocalDateTime mockCurrentTime = LocalDateTime.of(2024, 7, 17, 12, 0);
        LocalDateTime monday = mockCurrentTime.with(DayOfWeek.MONDAY);
        LocalDateTime chosenMonday = monday.plusDays(7 * week);
        LocalDateTime sunday = chosenMonday.plusDays(6);
        Manager manager = new Manager();
        manager.setId(1L);
        List<CheckList> checkLists = new ArrayList<>();
        WorkSchedule workScheduleOne = new WorkSchedule(
                1L,
                manager,
                new Pizzeria(),
                monday,
                monday.plusHours(8),
                checkLists);
        WorkSchedule workScheduleTwo = new WorkSchedule(
                2L,
                manager,
                new Pizzeria(),
                monday.plusDays(1),
                monday.plusHours(32),
                checkLists);


        try (MockedStatic<LocalDateTime> mocked = Mockito.mockStatic(LocalDateTime.class)) {
            mocked.when(LocalDateTime::now).thenReturn(mockCurrentTime);

            List<WorkSchedule> schedulesOfWeek = new ArrayList<>();
            schedulesOfWeek.add(workScheduleOne);
            schedulesOfWeek.add(workScheduleTwo);
            when(workScheduleRepository.findByPizzeria_IdAndStartTimeBetweenOrderByManager_SurnameAsc(pizzeriaId, chosenMonday.toLocalDate(), chosenMonday.plusDays(6).toLocalDate()))
                    .thenReturn(schedulesOfWeek);

            List<WeeklyScheduleShowDto> expectation = new ArrayList<>();
            expectation.add(new WeeklyScheduleShowDto());
            expectation.add(new WeeklyScheduleShowDto());

            //ToDo Здесь происходит использование метода createWeeklySchedule(), писать под него отдельный тест я не стал
            when(workScheduleRepository.findByManager_IdAndPizzeria_IdAndStartTime(manager.getId(), pizzeriaId, chosenMonday.toLocalDate()))
                    .thenReturn(Optional.of(workScheduleOne));
            when(dtoBuilder.buildManagerShowDto(manager)).thenReturn(new ManagerShowDto());

            when(dtoBuilder.buildPizzeriaDto(any())).thenReturn(PizzeriaDto.builder().id(1L).build());

            List<WeeklyScheduleShowDto> result = workScheduleService.getWeeklySchedulesByPizzeriaId(pizzeriaId, week);

            assertEquals(expectation.size(), result.size());
        }
    }

//    @Test
//    void testCreateWeeklySchedule() {
//
//    }

    @Test
    void testFindWorkScheduleByManagerAndDate() {
        WorkSchedule workSchedule = WorkSchedule.builder().build();
        when(workScheduleRepository.findByManager_IdAndStartTimeDate(1L, LocalDate.parse("2024-06-30") )).thenReturn(Optional.of(workSchedule));

        WorkSchedule foundWorkSchedule =  workScheduleService.findWorkScheduleByManagerAndDate(1L, LocalDate.parse("2024-06-30"));

        assertNotNull(foundWorkSchedule);
        assertEquals(workSchedule, foundWorkSchedule);
    }

    @Test
    void testFindWorkScheduleByManagerAndDate_ThrowsNoSuchElementException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
           workScheduleService.findWorkScheduleByManagerAndDate(1L, LocalDate.parse("2024-06-30"));
        });
        assertEquals(exception.getMessage(), "No such work_schedule");
    }

    @Test
    void testGetWorkSchedule(){
        WorkScheduleSupervisorShowDto showDto = WorkScheduleSupervisorShowDto.builder().build();
        WorkSchedule workSchedule = WorkSchedule.builder().build();
        when(workScheduleRepository.findByManager_IdAndStartTimeDate(1L, LocalDate.parse("2024-06-30") )).thenReturn(Optional.of(workSchedule));
        WorkSchedule foundWorkSchedule =  workScheduleService.findWorkScheduleByManagerAndDate(1L, LocalDate.parse("2024-06-30"));
        when(dtoBuilder.buildWorkScheduleShowDto(foundWorkSchedule)).thenReturn(showDto);

        WorkScheduleSupervisorShowDto returnedDto = workScheduleService.getWorkSchedule(1L, LocalDate.parse("2024-06-30"));

        assertNotNull(returnedDto);
        assertEquals(showDto, returnedDto);
    }

    @Test
    void testSave(){
        WorkSchedule workSchedule = WorkSchedule.builder().build();
        when(workScheduleRepository.save(workSchedule)).thenReturn(workSchedule);
        workScheduleService.save(workSchedule);

        verify(workScheduleRepository).save(workSchedule);
    }
}
