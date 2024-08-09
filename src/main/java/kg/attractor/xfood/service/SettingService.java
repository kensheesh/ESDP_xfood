package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.model.Setting;

import java.util.Map;

public interface SettingService {
    Setting getOpportunityDeadline();
    Setting getDayOffCount();
    Setting getAppealDeadline();
    void updateDeadlines(DeadlinesDto deadlines);
    boolean isCheckRecent(ChecklistShowDto dto);
    boolean isAvailableToDayOff(Map<String, OpportunityDto> opportunitiesMap);
}
