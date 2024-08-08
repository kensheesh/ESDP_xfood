package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.model.Setting;

public interface SettingService {
    Setting getOpportunityDeadlineValue();
    Setting getDayOffCount();

    Setting getAppealDeadlineValue();

    void updateDeadlines(DeadlinesDto deadlines);
}
