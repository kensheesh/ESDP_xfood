package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Setting;
import kg.attractor.xfood.repository.SettingRepository;
import kg.attractor.xfood.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {
    public final SettingRepository settingRepository;
    @Override
    public Setting getOpportunityDeadline() {
        return settingRepository.findByName("opportunity_deadline")
                .orElseThrow(() -> new NotFoundException("Таких настроек в БД не найдено"));
    }

    @Override
    public Setting getDayOffCount() {
        return settingRepository.findByName("day_off_count")
                .orElseThrow(() -> new NotFoundException("Таких настроек в БД не найдено"));
    }

    @Override
    public Setting getAppealDeadline() {
        return settingRepository.findByName("appeal_deadline")
                .orElseThrow(() -> new NotFoundException("Таких настроек в БД не найдено"));
    }

    @Override
    public void updateDeadlines(DeadlinesDto deadlines) {
        settingRepository.updateValueIntByName(deadlines.getOppDeadline(), "opportunity_deadline");
        settingRepository.updateValueIntByName(deadlines.getDayOff(), "day_off_count");
        settingRepository.updateValueIntByName(deadlines.getAppealDeadline(), "appeal_deadline");
    }

    @Override
    public boolean isCheckRecent(ChecklistShowDto dto) {
        LocalDateTime checkEndTime = dto.getEndTime();
        LocalDateTime currentTime = LocalDateTime.now();
        int appealCreateDeadline = getAppealDeadline().getValueInt();
        return checkEndTime.isAfter(currentTime.minusDays(appealCreateDeadline));
    }
}
