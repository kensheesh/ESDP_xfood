package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.service.OkHttpService;
import kg.attractor.xfood.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class SupervisorController {
    private final LocationServiceImpl locationService;
    private final PizzeriaServiceImpl pizzeriaService;
    private final WorkScheduleServiceImpl workScheduleService;
    private final OkHttpService okHttpService;

    @GetMapping("/weekly")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public String getWeeklySchedule (
            @RequestParam(name = "week", defaultValue = "0") long week,
            @RequestParam(name = "locId", defaultValue = "0") long locationId,
            @RequestParam(name = "pizzId", defaultValue = "0") long pizzeriaId,
            Model model) {
        
        if (pizzeriaId != 0) {
            okHttpService.getWorksheetOfPizzeriaManagers(pizzeriaId,AuthParams.getPrincipal().getUsername());
        }

        model.addAttribute("locationId", locationId);
        model.addAttribute("pizzeriaId", pizzeriaId);
        model.addAttribute("week", workScheduleService.getWeekInfo(week));
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("pizzerias", pizzeriaService.getPizzeriasByLocationId(locationId));
        model.addAttribute("schedules", workScheduleService.getWeeklySchedulesByPizzeriaId(pizzeriaId, week));

        return "weekly_schedules";
    }
}
