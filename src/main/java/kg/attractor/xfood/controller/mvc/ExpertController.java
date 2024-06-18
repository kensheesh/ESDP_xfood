package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.opportunity.OpportunityCreateWrapper;
import kg.attractor.xfood.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final OpportunityService opportunityService;

    @GetMapping("/checks")
    public String getChecks () {
        // TODO список назначенных проверок (все на сегодня и просроченные)
        return "TODO_sketches/checks";
    }

    @GetMapping ("/opportunities")
    public String getOpportunityMap (Model model) {
        model.addAttribute("opportunities", opportunityService.getAllByExpert());
        model.addAttribute("monday", LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue()-1));
        return "expert/opportunities";
    }

    @PostMapping("/opportunities/change")
    public String changeOpportunities (OpportunityCreateWrapper opportunities, Authentication auth) {
        opportunityService.changeExpertOpportunities(opportunities.getOpportunities(), auth);
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
