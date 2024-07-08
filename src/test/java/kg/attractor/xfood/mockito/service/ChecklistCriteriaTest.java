package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.service.impl.CheckListCriteriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChecklistCriteriaTest {
    @InjectMocks
    private CheckListCriteriaServiceImpl criteriaService;
    @Mock
    private ChecklistCriteriaRepository criteriaRepository;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllByChecklistId(){
        when(criteriaRepository.findAllByChecklistId(anyLong())).thenReturn(new ArrayList<>());
        List<CheckListsCriteria> criteriaLists = criteriaService.findAllByChecklistId(anyLong());
        assertNotNull(criteriaLists);
        verify(criteriaRepository, times(1)).findAllByChecklistId(anyLong());
    }

    @Test
    void testSave(){
        CheckListsCriteria criteria = CheckListsCriteria.builder().id(1L).build();
        when(criteriaRepository.save(any(CheckListsCriteria.class))).thenReturn(criteria);
        criteriaService.save(criteria);
        verify(criteriaRepository, times(1)).save(criteria);
    }

}
