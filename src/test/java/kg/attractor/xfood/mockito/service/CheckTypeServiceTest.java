package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.CheckTypeShowDto;
import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;
import kg.attractor.xfood.model.CheckType;
import kg.attractor.xfood.model.CriteriaType;
import kg.attractor.xfood.repository.CheckTypeRepository;
import kg.attractor.xfood.repository.CriteriaTypeRepository;
import kg.attractor.xfood.service.impl.CheckTypeServiceImpl;
import kg.attractor.xfood.service.impl.DtoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckTypeServiceTest {

    @InjectMocks
    private CheckTypeServiceImpl checkTypeService;

    @Mock
    private CheckTypeRepository checkTypeRepository;

    @Mock
    private CriteriaTypeRepository criteriaTypeRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTypes() {
        CheckType checkType = new CheckType();
        CheckTypeSupervisorViewDto dto = new CheckTypeSupervisorViewDto();
        when(checkTypeRepository.findAll()).thenReturn(List.of(checkType));
        when(dtoBuilder.buildCheckTypeShowDto(checkType)).thenReturn(dto);

        List<CheckTypeSupervisorViewDto> types = checkTypeService.getTypes();

        assertNotNull(types);
        assertEquals(1, types.size());
        assertEquals(dto, types.get(0));
        verify(checkTypeRepository, times(1)).findAll();
        verify(dtoBuilder, times(1)).buildCheckTypeShowDto(checkType);
    }

    @Test
    void testGetTypes_EmptyList() {
        when(checkTypeRepository.findAll()).thenReturn(new ArrayList<>());

        List<CheckTypeSupervisorViewDto> types = checkTypeService.getTypes();

        assertNotNull(types);
        assertTrue(types.isEmpty());
        verify(checkTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Long checkTypeId = 1L;
        CheckType checkType = new CheckType();
        when(checkTypeRepository.findById(checkTypeId)).thenReturn(Optional.of(checkType));

        CheckType result = checkTypeService.getById(checkTypeId);

        assertNotNull(result);
        assertEquals(checkType, result);
        verify(checkTypeRepository, times(1)).findById(checkTypeId);
    }

    @Test
    void testGetById_NotFound() {
        Long checkTypeId = 1L;
        when(checkTypeRepository.findById(checkTypeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> checkTypeService.getById(checkTypeId));

        verify(checkTypeRepository, times(1)).findById(checkTypeId);
    }


    @Test
    void testGetCheckTypes_EmptyList() {
        when(checkTypeRepository.findByOrderByNameAsc()).thenReturn(new ArrayList<>());

        List<CheckTypeShowDto> dtoList = checkTypeService.getCheckTypes();

        assertNotNull(dtoList);
        assertTrue(dtoList.isEmpty());
        verify(checkTypeRepository, times(1)).findByOrderByNameAsc();
    }

    @Test
    void testFindByName() {
        String typeName = "Test Type";
        CheckType checkType = new CheckType();
        when(checkTypeRepository.findByName(typeName)).thenReturn(Optional.of(checkType));

        CheckType result = checkTypeService.findByName(typeName);

        assertNotNull(result);
        assertEquals(checkType, result);
        verify(checkTypeRepository, times(1)).findByName(typeName);
    }

    @Test
    void testFindByName_NotFound() {
        String typeName = "Nonexistent Type";
        when(checkTypeRepository.findByName(typeName)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> checkTypeService.findByName(typeName));

        verify(checkTypeRepository, times(1)).findByName(typeName);
    }

    @Test
    void testSave() {
        CheckType checkType = new CheckType();
        checkTypeService.save(checkType);
        verify(checkTypeRepository, times(1)).save(checkType);
    }

    @Test
    void testExistsByName() {
        String value = "Existing Type";
        when(checkTypeRepository.existsByName(value)).thenReturn(true);

        boolean exists = checkTypeService.existsByName(value);

        assertTrue(exists);
        verify(checkTypeRepository, times(1)).existsByName(value);
    }

    @Test
    void testExistsByName_NotFound() {
        String value = "Nonexistent Type";
        when(checkTypeRepository.existsByName(value)).thenReturn(false);

        boolean exists = checkTypeService.existsByName(value);

        assertFalse(exists);
        verify(checkTypeRepository, times(1)).existsByName(value);
    }

    @Test
    void testDelete() {
        CriteriaType criteriaType = new CriteriaType();
        checkTypeService.delete(criteriaType);
        verify(criteriaTypeRepository, times(1)).delete(criteriaType);
    }

    @Test
    void testGetTotalMaxValueByTypeId() {
        Long typeId = 1L;
        CriteriaType criteriaType1 = new CriteriaType();
        criteriaType1.setMaxValue(10);
        CriteriaType criteriaType2 = new CriteriaType();
        criteriaType2.setMaxValue(20);
        when(criteriaTypeRepository.findByType_IdAndCriteria_Section_IdOrderByType_NameAsc(typeId, 3L))
                .thenReturn(List.of(criteriaType1, criteriaType2));

        int totalMaxValue = checkTypeService.getTotalMaxValueByTypeId(typeId);

        assertEquals(30, totalMaxValue);
        verify(criteriaTypeRepository, times(1)).findByType_IdAndCriteria_Section_IdOrderByType_NameAsc(typeId, 3L);
    }

    @Test
    void testGetTotalMaxValueByTypeId_NoCriteria() {
        Long typeId = 2L;
        when(criteriaTypeRepository.findByType_IdAndCriteria_Section_IdOrderByType_NameAsc(typeId, 3L))
                .thenReturn(new ArrayList<>());

        int totalMaxValue = checkTypeService.getTotalMaxValueByTypeId(typeId);

        assertEquals(0, totalMaxValue);
        verify(criteriaTypeRepository, times(1)).findByType_IdAndCriteria_Section_IdOrderByType_NameAsc(typeId, 3L);
    }
}