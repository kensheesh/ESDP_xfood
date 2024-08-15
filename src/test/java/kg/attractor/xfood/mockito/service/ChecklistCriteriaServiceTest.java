package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ChecklistCriteriaServiceTest {

    @InjectMocks
    private CheckListCriteriaServiceImpl checkListCriteriaService;
    @Mock
    private ChecklistCriteriaRepository checklistCriteriaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllByChecklistId() {
        when(checklistCriteriaRepository.findAllByChecklistId(anyLong())).thenReturn(new ArrayList<>());
        List<CheckListsCriteria> criteriaLists = checkListCriteriaService.findAllByChecklistId(anyLong());
        assertNotNull(criteriaLists);
        verify(checklistCriteriaRepository, times(1)).findAllByChecklistId(anyLong());
    }

    @Test
    void testSave() {
        CheckListsCriteria criteria = CheckListsCriteria.builder().id(1L).build();
        when(checklistCriteriaRepository.save(any(CheckListsCriteria.class))).thenReturn(criteria);
        checkListCriteriaService.save(criteria);
        verify(checklistCriteriaRepository, times(1)).save(criteria);
    }

    @Test
    void testCreateCriteriaFactor_EmptyDescription() {
        SaveCriteriaDto saveCriteriaDto = new SaveCriteriaDto();
        assertThrows(IllegalArgumentException.class, () -> checkListCriteriaService.createCritFactor(saveCriteriaDto, ""));
    }


    @Test
    void testDeleteCriterionByChecklist() {
        List<CheckListsCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new CheckListsCriteria());
        when(checklistCriteriaRepository.findAllByChecklistId(anyLong())).thenReturn(criteriaList);

        checkListCriteriaService.deleteCriterionByChecklist(1L);
        verify(checklistCriteriaRepository, times(1)).deleteAll(criteriaList);
    }


    @Test
    void testCreateNewFactor_ExistingCriteria() {
        SaveCriteriaDto saveCriteriaDto = new SaveCriteriaDto();
        saveCriteriaDto.setCheckListId(1L);
        saveCriteriaDto.setCriteriaId(2L);
        saveCriteriaDto.setValue(10);

        CheckListsCriteria existingCriteria = new CheckListsCriteria();
        existingCriteria.setId(1L);

        when(checklistCriteriaRepository.findByCriteria_IdAndAndChecklist_Id(anyLong(), anyLong()))
                .thenReturn(Optional.of(existingCriteria));

        assertThrows(NullPointerException.class, () -> checkListCriteriaService.createNewFactor(saveCriteriaDto));
    }

}
