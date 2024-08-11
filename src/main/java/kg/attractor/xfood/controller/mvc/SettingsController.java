package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.dto.settings.TemplateCreateDto;
import kg.attractor.xfood.service.SectionService;
import kg.attractor.xfood.service.ZoneService;
import kg.attractor.xfood.service.impl.CheckTypeServiceImpl;
import kg.attractor.xfood.service.impl.SettingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final ZoneService zoneService;
    private final SectionService sectionService;

    @GetMapping("/deadlines")
    public String getDeadlines(Model model) {
        model.addAttribute("oppDeadlineSetting", settingService.getOpportunityDeadline());
        model.addAttribute("dayOffSetting", settingService.getDayOffCount());
        model.addAttribute("appealDeadlineSetting", settingService.getAppealDeadline());
        return "settings/deadlines";
    }

    @PostMapping("/deadlines")
    public String updateDeadlines(@RequestBody DeadlinesDto deadlines) {
        log.info("Settings: " + deadlines.getOppDeadline() + ", " + deadlines.getDayOff()  + ", " + deadlines.getAppealDeadline());
        settingService.updateDeadlines(deadlines);
        return "redirect:/deadlines";
    }

    @GetMapping("/templates")
//    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public String getTemplates (Model model) {
        model.addAttribute("templates", checkTypeService.getCheckTypes());
        return "settings/templates";
    }


    @GetMapping("/templates/create")
    public String getTemplatesCreate (Model model) {
        model.addAttribute("zones", zoneService.getZones());
        model.addAttribute("sections", sectionService.getSections());
        model.addAttribute("templateCreateDto", new TemplateCreateDto());
        return "settings/template_create";
    }
}
