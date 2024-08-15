package kg.attractor.xfood.mockito.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import kg.attractor.xfood.model.Shift;
import kg.attractor.xfood.repository.ShiftRepository;
import kg.attractor.xfood.service.impl.ShiftServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

class ShiftServiceTest {

    @Mock
    private ShiftRepository shiftRepository;

    @InjectMocks
    private ShiftServiceImpl shiftService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetShiftsByOpportunityId() {
        long opportunityId = 1L;
        Shift shift1 = new Shift();
        shift1.setStartTime(LocalTime.from(LocalDateTime.now().minusHours(1)));
        Shift shift2 = new Shift();
        shift2.setStartTime(LocalTime.from(LocalDateTime.now()));

        when(shiftRepository.findByOpportunity_IdOrderByStartTimeAsc(opportunityId))
                .thenReturn(List.of(shift1, shift2));

        List<Shift> result = shiftService.getShiftsByOpportunityId(opportunityId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(shift1, result.get(0));
        assertEquals(shift2, result.get(1));
    }

    @Test
    void testDeleteAllByIds() {
        List<Long> ids = List.of(1L, 2L, 3L);

        shiftService.deleteAllByIds(ids);

        verify(shiftRepository).deleteAllById(ids);
    }

    @Test
    void testDeleteAllByOpportunityId() {
        Long opportunityId = 1L;

        shiftService.deleteAllByOpportunityId(opportunityId);

        verify(shiftRepository).deleteAllByOpportunityId(opportunityId);
    }

    @Test
    void testSaveAll() {
        Shift shift1 = new Shift();
        Shift shift2 = new Shift();
        List<Shift> shifts = List.of(shift1, shift2);

        shiftService.saveAll(shifts);

        verify(shiftRepository).saveAll(shifts);
    }

    @Test
    void testGetAllByOpportunityId() {
        Long opportunityId = 1L;
        List<Long> shiftIds = List.of(1L, 2L, 3L);

        when(shiftRepository.findAllIdsByOpportunityId(opportunityId)).thenReturn(shiftIds);

        List<Long> result = shiftService.getAllByOpportunityId(opportunityId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(shiftIds));
    }
}