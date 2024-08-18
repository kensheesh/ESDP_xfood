package kg.attractor.xfood.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.dto.settings.TemplateCreateDto;
import kg.attractor.xfood.dto.settings.TemplateUpdateDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.service.CheckTypeService;
import kg.attractor.xfood.service.SectionService;
import kg.attractor.xfood.service.SettingService;
import kg.attractor.xfood.service.ZoneService;
import kg.attractor.xfood.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final UserServiceImpl userService;

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
        model.addAttribute("sections", sectionService.getSectionsWithoutCritAndWow());
        model.addAttribute("templateCreateDto", new TemplateCreateDto());
        return "settings/template_create";
    }

    @PostMapping("/templates/create")
    public String TemplatesCreate (@Valid TemplateCreateDto templateCreateDto,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("zones", zoneService.getZones());
            model.addAttribute("sections", sectionService.getSectionsWithoutCritAndWow());
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
        model.addAttribute("sections", sectionService.getSectionsWithoutCritAndWow());
        model.addAttribute("template", settingService.getTemplate(id));
        model.addAttribute("templateUpdateDto", new TemplateUpdateDto());
        model.addAttribute("id", id);
        return "settings/template_edit";
    }

    @PostMapping("/templates/{id}")
    public String updateTemplate( @PathVariable Long id ,@Valid TemplateUpdateDto templateUpdateDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("zones", zoneService.getZones());
            model.addAttribute("template", settingService.getTemplate(id));
            model.addAttribute("sections", sectionService.getSectionsWithoutCritAndWow());
            model.addAttribute("templateUpdateDto", templateUpdateDto);
            model.addAttribute("id", id);
            return "settings/template_edit";
        }
        settingService.updateTemplate(id, templateUpdateDto);
        return "redirect:/templates";
    }

    @GetMapping("users")
    public String getUsers(Model model,
                           @RequestParam(name = "role", defaultValue = "default", required = false) String role,
                           @RequestParam(name = "page", defaultValue = "0") String page,
                           @RequestParam(name = "size", defaultValue = "4") String size,
                           @RequestParam(name = "search", defaultValue = "", required = false) String search) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        Page<UserDto> userPage = userService.getAllUsers(role, pageable, search);
        UserDto currentUser = userService.getUserDto();
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentRole", role);
        model.addAttribute("searchWord", search);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("currentSize", Integer.parseInt(size));
        return "users/users";
    }
}
