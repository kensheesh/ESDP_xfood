package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.dto.workSchedule.WorkScheduleCreateDto;
import kg.attractor.xfood.service.impl.CheckTypeServiceImpl;
import kg.attractor.xfood.service.impl.SettingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping()
public class SettingsController {
    public final SettingServiceImpl settingService;
    private final CheckTypeServiceImpl checkTypeService;

    @GetMapping("/deadlines")
    public String getDeadlines(Model model) {
        model.addAttribute("oppDeadlineSetting", settingService.getOpportunityDeadlineValue());
        model.addAttribute("dayOffSetting", settingService.getDayOffCount());
        model.addAttribute("appealDeadlineSetting", settingService.getAppealDeadlineValue());
        return "settings/deadlines";
    }

    @PostMapping("/deadlines")
    public String updateDeadlines(DeadlinesDto deadlines) {
        settingService.updateDeadlines(deadlines);
        return "redirect:/deadlines";
    }

    @GetMapping("/templates")
//    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public String getTemplates (Model model) {
        model.addAttribute("templates", checkTypeService.getCheckTypes());
        return "settings/templates";
    }


}
