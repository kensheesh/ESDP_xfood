package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.OkHttpService;
import kg.attractor.xfood.service.impl.LocationServiceImpl;
import kg.attractor.xfood.service.impl.PizzeriaServiceImpl;
import kg.attractor.xfood.service.impl.WorkScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supervisor")
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class SupervisorController {
    private final LocationServiceImpl locationService;
    private final PizzeriaServiceImpl pizzeriaService;
    private final WorkScheduleServiceImpl workScheduleService;
    private final OkHttpService okHttpService;


    @GetMapping("/opportunity-map")
    public String getOpportunityMap (Model model) {
        /* TODO
            Карта возможностей + назначение проверок + переход на редактирование шаблонов
        */
        return null;
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
            okHttpService.getWorksheetOfPizzeriaManagers(pizzeriaId);
        }

        model.addAttribute("locationId", locationId);
        model.addAttribute("pizzeriaId", pizzeriaId);
        model.addAttribute("week", workScheduleService.getWeekInfo(week));
        model.addAttribute("locations", locationService.getLocations());
        model.addAttribute("pizzerias", pizzeriaService.getPizzeriasByLocationId(locationId));
        model.addAttribute("schedules", workScheduleService.getWeeklySchedulesByPizzeriaId(pizzeriaId, week));
        return "weekly";
    }
}
