package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.model.Setting;

public interface SettingService {
    Setting getOpportunityDeadline();
    Setting getDayOffCount();
    Setting getAppealDeadline();
    void updateDeadlines(DeadlinesDto deadlines);
    boolean isCheckRecent(ChecklistShowDto dto);
}
