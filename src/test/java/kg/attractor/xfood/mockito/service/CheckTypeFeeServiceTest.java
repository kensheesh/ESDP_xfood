package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.exception.FeesNotFoundException;
import kg.attractor.xfood.model.CheckTypeFee;
import kg.attractor.xfood.repository.CheckTypeFeeRepository;
import kg.attractor.xfood.service.impl.CheckTypeFeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckTypeFeeServiceTest {

    @InjectMocks
    private CheckTypeFeeServiceImpl checkTypeFeeService;

    @Mock
    private CheckTypeFeeRepository checkTypeFeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFeesByCheckTypeId() {
        Long checkTypeId = 1L;
        CheckTypeFee checkTypeFee = new CheckTypeFee();
        checkTypeFee.setFees(BigDecimal.valueOf(100.00));
        when(checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(checkTypeId))
                .thenReturn(Optional.of(checkTypeFee));

        BigDecimal fees = checkTypeFeeService.getFeesByCheckTypeId(checkTypeId);

        assertNotNull(fees);
        assertEquals(BigDecimal.valueOf(100.00), fees);
        verify(checkTypeFeeRepository, times(1)).findByCheckType_IdAndEnabledTrue(checkTypeId);
    }

    @Test
    void testGetFeesByCheckTypeId_FeesNotFound() {
        Long checkTypeId = 1L;
        when(checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(checkTypeId))
                .thenReturn(Optional.empty());

        assertThrows(FeesNotFoundException.class, () -> checkTypeFeeService.getFeesByCheckTypeId(checkTypeId));

        verify(checkTypeFeeRepository, times(1)).findByCheckType_IdAndEnabledTrue(checkTypeId);
    }

    @Test
    void testSave() {
        CheckTypeFee checkTypeFee = new CheckTypeFee();
        checkTypeFeeService.save(checkTypeFee);
        verify(checkTypeFeeRepository, times(1)).save(checkTypeFee);
    }

    @Test
    void testGetCheckTypeFeeByTypeId() {
        Long id = 1L;
        CheckTypeFee checkTypeFee = new CheckTypeFee();
        checkTypeFee.setFees(BigDecimal.valueOf(100.00));
        when(checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(id))
                .thenReturn(Optional.of(checkTypeFee));

        CheckTypeFee result = checkTypeFeeService.getCheckTypeFeeByTypeId(id);

        assertNotNull(result);
        assertEquals(checkTypeFee, result);
        verify(checkTypeFeeRepository, times(1)).findByCheckType_IdAndEnabledTrue(id);
    }

    @Test
    void testGetCheckTypeFeeByTypeId_FeesNotFound() {
        Long id = 1L;
        when(checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(id))
                .thenReturn(Optional.empty());

        assertThrows(FeesNotFoundException.class, () -> checkTypeFeeService.getCheckTypeFeeByTypeId(id));

        verify(checkTypeFeeRepository, times(1)).findByCheckType_IdAndEnabledTrue(id);
    }

    @Test
    void testGetFeesByCheckTypeId_TwoCalls() {
        Long checkTypeId = 3L;
        CheckTypeFee checkTypeFee = new CheckTypeFee();
        checkTypeFee.setFees(BigDecimal.valueOf(200.00));
        when(checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(checkTypeId))
                .thenReturn(Optional.of(checkTypeFee));

        BigDecimal fees1 = checkTypeFeeService.getFeesByCheckTypeId(checkTypeId);
        BigDecimal fees2 = checkTypeFeeService.getFeesByCheckTypeId(checkTypeId);

        assertNotNull(fees1);
        assertEquals(BigDecimal.valueOf(200.00), fees1);
        assertNotNull(fees2);
        assertEquals(BigDecimal.valueOf(200.00), fees2);
        verify(checkTypeFeeRepository, times(2)).findByCheckType_IdAndEnabledTrue(checkTypeId);
    }

    @Test
    void testSave_CheckTypeFeeWithFees() {
        CheckTypeFee checkTypeFee = new CheckTypeFee();
        checkTypeFee.setFees(BigDecimal.valueOf(300.00));
        when(checkTypeFeeRepository.save(checkTypeFee)).thenReturn(checkTypeFee);
        checkTypeFeeService.save(checkTypeFee);
        verify(checkTypeFeeRepository, times(1)).save(checkTypeFee);
    }

    @Test
    void testGetCheckTypeFeeByTypeId_WithDifferentIds() {
        Long id1 = 4L;
        Long id2 = 5L;
        CheckTypeFee checkTypeFee1 = new CheckTypeFee();
        checkTypeFee1.setFees(BigDecimal.valueOf(250.00));
        CheckTypeFee checkTypeFee2 = new CheckTypeFee();
        checkTypeFee2.setFees(BigDecimal.valueOf(350.00));
        when(checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(id1))
                .thenReturn(Optional.of(checkTypeFee1));
        when(checkTypeFeeRepository.findByCheckType_IdAndEnabledTrue(id2))
                .thenReturn(Optional.of(checkTypeFee2));

        CheckTypeFee result1 = checkTypeFeeService.getCheckTypeFeeByTypeId(id1);
        CheckTypeFee result2 = checkTypeFeeService.getCheckTypeFeeByTypeId(id2);

        assertNotNull(result1);
        assertEquals(BigDecimal.valueOf(250.00), result1.getFees());
        assertNotNull(result2);
        assertEquals(BigDecimal.valueOf(350.00), result2.getFees());
        verify(checkTypeFeeRepository, times(1)).findByCheckType_IdAndEnabledTrue(id1);
        verify(checkTypeFeeRepository, times(1)).findByCheckType_IdAndEnabledTrue(id2);
    }
}