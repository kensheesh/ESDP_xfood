package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.WorkScheduleSupervisorShowDto;
import kg.attractor.xfood.model.WorkSchedule;
import kg.attractor.xfood.repository.WorkScheduleRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.WorkScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

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
