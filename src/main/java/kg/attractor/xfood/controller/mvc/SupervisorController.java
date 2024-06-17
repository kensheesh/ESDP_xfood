package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.impl.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supervisor")
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class SupervisorController {
    private final LocationServiceImpl locationService;

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
    public String getWeeklySchedule (Model model) {
        model.addAttribute("locations", locationService.getLocations());
        return "weekly";
    }
}
