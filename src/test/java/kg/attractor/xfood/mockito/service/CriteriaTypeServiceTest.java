package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.model.CriteriaType;
import kg.attractor.xfood.repository.CriteriaTypeRepository;
import kg.attractor.xfood.service.CriteriaTypeService;
import kg.attractor.xfood.service.impl.CriteriaTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class CriteriaTypeServiceTest {
    @InjectMocks
    private CriteriaTypeServiceImpl criteriaTypeService;

    @Mock
    private CriteriaTypeRepository criteriaTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave(){
        CriteriaType criteriaType = new CriteriaType();
        when(criteriaTypeRepository.save(criteriaType)).thenReturn(criteriaType);
        criteriaTypeService.save(criteriaType);
        verify(criteriaTypeRepository, times(1)).save(criteriaType);
    }
}
