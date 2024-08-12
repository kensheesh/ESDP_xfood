package kg.attractor.xfood.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.dto.settings.TemplateCreateDto;
import kg.attractor.xfood.service.CheckTypeService;
import kg.attractor.xfood.service.SectionService;
import kg.attractor.xfood.service.SettingService;
import kg.attractor.xfood.service.ZoneService;
import kg.attractor.xfood.service.impl.CheckTypeServiceImpl;
import kg.attractor.xfood.service.impl.SettingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping()
public class SettingsController {
    public final SettingService settingService;
    private final CheckTypeService checkTypeService;
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

    @PostMapping("/templates/create")
    public String TemplatesCreate (@Valid TemplateCreateDto templateCreateDto,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("zones", zoneService.getZones());
            model.addAttribute("sections", sectionService.getSections());
            model.addAttribute("templateCreateDto", templateCreateDto);
            return "settings/template_create";
        }
        model.addAttribute("zones", zoneService.getZones());
        model.addAttribute("sections", sectionService.getSections());
        settingService.createTemplate(templateCreateDto);
        return "redirect:/templates";
    }

    @GetMapping("/templates/{id}")
    public String getTemplateDetail (@PathVariable Long id, Model model) {
        model.addAttribute("zones", zoneService.getZones());
        model.addAttribute("sections", sectionService.getSections());
        model.addAttribute("template", settingService.getTemplate(id));
        model.addAttribute("templateCreateDto", new TemplateCreateDto());
        return "settings/template_edit";
    }
}
