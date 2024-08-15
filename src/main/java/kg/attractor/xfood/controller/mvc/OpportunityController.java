package kg.attractor.xfood.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.workSchedule.WorkScheduleCreateDto;
import kg.attractor.xfood.service.OpportunityService;
import kg.attractor.xfood.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class OpportunityController {
    private final OpportunityService opportunityService;
    private final SettingServiceImpl settingService;
    private final CheckListServiceImpl checkListService;
    private final WorkScheduleServiceImpl workScheduleService;
    private final PizzeriaServiceImpl pizzeriaService;
    private final ManagerServiceImpl managerService;

    @GetMapping ("/my-opportunities")
    public String getOpportunityMap (Model model) {
        Map<String, OpportunityDto> opportunitiesMap = opportunityService.getAllByExpert();
        LocalDate monday = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue()-1);
        model.addAttribute("opportunities", opportunitiesMap);
        model.addAttribute("monday", monday);
        model.addAttribute("isAvailableToChange", settingService.isAvailableToChange(monday));
        model.addAttribute("isAvailableToDayOff", settingService.isAvailableToDayOff(opportunitiesMap));
        model.addAttribute("checksCount", checkListService.getAmountOfNewChecks());
        return "opportunities/my-opportunities";
    }

    @PostMapping("/my-opportunities/change")
    public String changeOpportunities (@Valid OpportunityCreateDto opportunity) {
        opportunityService.changeExpertOpportunity(opportunity);
        return "redirect:/my-opportunities";
    }

    @GetMapping("/opportunities")
    public String getOpportunityMap (
            @RequestParam(name = "week", defaultValue = "0") long week,
            @RequestParam(name = "search", defaultValue = "") String search,
            Model model) {

        model.addAttribute("week", workScheduleService.getWeekInfo(week));
        model.addAttribute("opportunities", opportunityService.getWeeklyOpportunities(week, search));
        model.addAttribute("pizzerias", pizzeriaService.getAllPizzerias());
        model.addAttribute("managers", managerService.getAllManagers());

        return "weekly_opportunities";
    }

    @PostMapping("/opportunities")
    public String prepareScheduleForCheck(WorkScheduleCreateDto schedule) {
        workScheduleService.prepareScheduleForCheck(schedule);
        return "redirect:/checks/create?date=" + schedule.getCurrentDate() + "&managerId=" + schedule.getManagerId() + "&expertId=" + schedule.getExpertId();
    }
}
