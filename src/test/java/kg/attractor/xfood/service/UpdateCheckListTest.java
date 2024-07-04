package kg.attractor.xfood.service;

import kg.attractor.xfood.dao.CheckListDao;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.IncorrectDateException;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.impl.CheckListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UpdateCheckListTest {

    @Mock
    private CheckListDao checkListDao;
    @Mock
    private CheckListRepository checkListRepository;
    @InjectMocks
    private CheckListServiceImpl checkListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateCheckStatusCheckList_successfulUpdate() {
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
    public void testUpdateCheckStatusCheckList_alreadyDone() {
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
    public void testUpdateCheckStatusCheckList_durationNotSpecified() {
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
}
