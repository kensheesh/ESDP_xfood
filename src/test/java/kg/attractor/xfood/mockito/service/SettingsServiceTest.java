package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Setting;
import kg.attractor.xfood.repository.SettingRepository;
import kg.attractor.xfood.service.impl.SettingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SettingsServiceTest {

    @Mock
    private SettingRepository settingRepository;

    @InjectMocks
    private SettingServiceImpl settingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOpportunityDeadline() {
        Setting setting = new Setting();
        setting.setName("opportunity_deadline");
        setting.setValueInt(5);

        when(settingRepository.findByName("opportunity_deadline")).thenReturn(Optional.of(setting));

        Setting result = settingService.getOpportunityDeadline();

        assertNotNull(result);
        assertEquals(5, result.getValueInt());
    }

    @Test
    void testGetOpportunityDeadlineNotFound() {
        when(settingRepository.findByName("opportunity_deadline")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> settingService.getOpportunityDeadline());
    }

    @Test
    void testGetDayOffCount() {
        Setting setting = new Setting();
        setting.setName("day_off_count");
        setting.setValueInt(2);

        when(settingRepository.findByName("day_off_count")).thenReturn(Optional.of(setting));

        Setting result = settingService.getDayOffCount();

        assertNotNull(result);
        assertEquals(2, result.getValueInt());
    }

    @Test
    void testGetDayOffCountNotFound() {
        when(settingRepository.findByName("day_off_count")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> settingService.getDayOffCount());
    }

    @Test
    void testGetAppealDeadline() {
        Setting setting = new Setting();
        setting.setName("appeal_deadline");
        setting.setValueInt(10);

        when(settingRepository.findByName("appeal_deadline")).thenReturn(Optional.of(setting));

        Setting result = settingService.getAppealDeadline();

        assertNotNull(result);
        assertEquals(10, result.getValueInt());
    }

    @Test
    void testGetAppealDeadlineNotFound() {
        when(settingRepository.findByName("appeal_deadline")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> settingService.getAppealDeadline());
    }

    @Test
    void testUpdateDeadlines() {
        DeadlinesDto deadlinesDto = new DeadlinesDto();
        deadlinesDto.setOppDeadline(7);
        deadlinesDto.setDayOff(3);
        deadlinesDto.setAppealDeadline(14);

        settingService.updateDeadlines(deadlinesDto);

        verify(settingRepository).updateValueIntByName(7, "opportunity_deadline");
        verify(settingRepository).updateValueIntByName(3, "day_off_count");
        verify(settingRepository).updateValueIntByName(14, "appeal_deadline");
    }


    @Test
    void testIsCheckRecentTrue() {
        Setting setting = new Setting();
        setting.setName("appeal_deadline");
        setting.setValueInt(10);

        when(settingRepository.findByName("appeal_deadline")).thenReturn(Optional.of(setting));

        ChecklistShowDto dto = new ChecklistShowDto();
        dto.setEndTime(LocalDateTime.now().minusDays(5));

        boolean result = settingService.isCheckRecent(dto);

        assertTrue(result);
    }

    @Test
    void testIsCheckRecentFalse() {
        Setting setting = new Setting();
        setting.setName("appeal_deadline");
        setting.setValueInt(10);

        when(settingRepository.findByName("appeal_deadline")).thenReturn(Optional.of(setting));

        ChecklistShowDto dto = new ChecklistShowDto();
        dto.setEndTime(LocalDateTime.now().minusDays(15));

        boolean result = settingService.isCheckRecent(dto);

        assertFalse(result);
    }

    @Test
    void testIsAvailableToChangeTrue() {
        Setting setting = new Setting();
        setting.setName("opportunity_deadline");
        setting.setValueInt(7);

        when(settingRepository.findByName("opportunity_deadline")).thenReturn(Optional.of(setting));

        LocalDate monday = LocalDate.now().plusDays(10);
        boolean result = settingService.isAvailableToChange(monday);

        assertTrue(result);
    }

    @Test
    void testIsAvailableToChangeFalse() {
        Setting setting = new Setting();
        setting.setName("opportunity_deadline");
        setting.setValueInt(7);

        when(settingRepository.findByName("opportunity_deadline")).thenReturn(Optional.of(setting));

        LocalDate monday = LocalDate.now().minusDays(5);
        boolean result = settingService.isAvailableToChange(monday);

        assertFalse(result);
    }
}