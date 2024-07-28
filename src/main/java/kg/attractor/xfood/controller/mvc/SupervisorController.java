package kg.attractor.xfood.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.workSchedule.WorkScheduleCreateDto;
import kg.attractor.xfood.model.WorkSchedule;
import kg.attractor.xfood.service.OkHttpService;
import kg.attractor.xfood.service.impl.LocationServiceImpl;
import kg.attractor.xfood.service.impl.ManagerServiceImpl;
import kg.attractor.xfood.service.impl.OpportunityServiceImpl;
import kg.attractor.xfood.service.impl.PizzeriaServiceImpl;
import kg.attractor.xfood.service.impl.WorkScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supervisor")
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class SupervisorController {
    private final LocationServiceImpl locationService;
    private final PizzeriaServiceImpl pizzeriaService;
    private final ManagerServiceImpl managerService;
    private final WorkScheduleServiceImpl workScheduleService;
    private final OpportunityServiceImpl opportunityService;
    private final OkHttpService okHttpService;
    private final AuthParams authParams;
    
    
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

    @GetMapping("/profile")
    public String getProfile (Model model) {
        /* TODO
            Профиль
        */
        return null;
    }
    
    @GetMapping("/weekly")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public String getWeeklySchedule (
            @RequestParam(name = "locId", defaultValue = "0") long locationId,
            @RequestParam(name = "pizzId", defaultValue = "0") long pizzeriaId,
            @RequestParam(name = "week", defaultValue = "0") long week,
            Model model) {
        
        if (pizzeriaId != 0) {
            okHttpService.getWorksheetOfPizzeriaManagers(pizzeriaId,AuthParams.getPrincipal().getUsername());
        }

        model.addAttribute("locationId", locationId);
        model.addAttribute("pizzeriaId", pizzeriaId);
        model.addAttribute("week", workScheduleService.getWeekInfo(week));
        model.addAttribute("locations", locationService.getLocations());
        model.addAttribute("pizzerias", pizzeriaService.getPizzeriasByLocationId(locationId));
        model.addAttribute("schedules", workScheduleService.getWeeklySchedulesByPizzeriaId(pizzeriaId, week));
        return "weekly_schedules";
    }
}
