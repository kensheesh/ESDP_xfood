package kg.attractor.xfood.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.service.OpportunityService;
import kg.attractor.xfood.service.impl.SettingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final OpportunityService opportunityService;
    private final SettingServiceImpl settingService;

    @GetMapping("/checks")
    public String getChecks () {
        // TODO список назначенных проверок (все на сегодня и просроченные)
        return "TODO_sketches/checks";
    }

    @GetMapping ("/opportunities")
    @PreAuthorize("hasRole('EXPERT')")
    public String getOpportunityMap (@RequestParam (name = "w", defaultValue = "1") int week, Model model) {
        Map<String, OpportunityDto> opportunitiesMap = opportunityService.getAllByExpert(week);
        LocalDate monday = LocalDate.now().plusWeeks(week).minusDays(LocalDate.now().getDayOfWeek().getValue()-1);
        model.addAttribute("opportunities", opportunitiesMap);
        model.addAttribute("monday", monday);
        model.addAttribute("isAvailableToChange", settingService.isAvailableToChange(monday));
        model.addAttribute("isAvailableToDayOff", settingService.isAvailableToDayOff(opportunitiesMap));
        model.addAttribute("week", week);
        return "expert/opportunities";
    }

    @PostMapping("/opportunities/change")
    @PreAuthorize("hasRole('EXPERT')")
    public String changeOpportunities (@Valid OpportunityCreateDto opportunity) {
        opportunityService.changeExpertOpportunity(opportunity);
        return "redirect:/expert/opportunities";
    }

    @GetMapping("/profile")
    public String getProfile (Model model) {
        /* TODO Профиль эксперта.
            Можно настроить часовые пояса,
            увидеть (редактировать?) информацию об аккаунте
        */
        return null;
    }

    @GetMapping("/profile/rewards")
    public String getRewards (Model model) {
        /* TODO
            История начислений за смену.
            Можно перейти из страницы профиля
        */

        return null;
    }

    @GetMapping ("/profile/history")
    public String getHistory (Model model) {
        return null;
        /* TODO
            История проверок эксперта.
            Можно перейти из страницы профиля
        */
    }
}
