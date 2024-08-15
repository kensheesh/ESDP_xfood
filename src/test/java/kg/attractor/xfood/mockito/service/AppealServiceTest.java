package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.exception.AppealNotFoundException;
import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.service.FileService;
import kg.attractor.xfood.service.impl.AppealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppealServiceTest {

    @Mock
    private AppealRepository appealRepository;

    @Mock
    private FileService fileService;

    @InjectMocks
    private AppealServiceImpl appealService;

    private CreateAppealDto createAppealDto;
    private Appeal appeal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        appeal = Appeal.builder()
                .id(1L)
                .email("original@email.test")
                .linkToExternalSrc("testLink")
                .tgLinkMessage("testLinkMessage")
                .fullName("fullName")
                .isAccepted(false)
                .checkListsCriteria(new CheckListsCriteria())
                .build();

        createAppealDto = CreateAppealDto.builder()
                .checkListCriteriaId(1L)
                .comment("comment")
                .inspectionDate(Date.from(Instant.now()))
                .email("updated@email.test")
                .linkToExternalSrc("testLink")
                .tgLinkMessage("testLinkMessage")
                .fullName("fullName")
                .files(new MultipartFile[0])
                .build();

        when(appealRepository.save(any(Appeal.class))).thenReturn(appeal);
    }

    @Test
    void updateTestWhenIdIsNotFound() {
        when(appealRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AppealNotFoundException.class, () -> appealService.update(createAppealDto, 2L));

        String expectedMessage = "Аппеляция не найдена";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateTestWhenIdExists() {
        when(appealRepository.findById(appeal.getId())).thenReturn(Optional.of(appeal));

        appealService.update(createAppealDto, appeal.getId());

        assertEquals("updated@email.test", appeal.getEmail());

        verify(appealRepository, times(1)).save(any(Appeal.class));
        verify(fileService, times(1)).saveFiles(anyList(), eq(appeal.getId()));
    }

    @Test
    void getAmountOfNewAppealsTest() {
        when(appealRepository.countAllByIsAcceptedNull()).thenReturn(5);

        Integer result = appealService.getAmountOfNewAppeals();

        assertEquals(5, result);
    }
}