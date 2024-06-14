package kg.attractor.xfood.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {

    @GetMapping("/checks")
    public String getChecks () {
        // TODO список назначенных проверок (все на сегодня и просроченные)
        return "TODO_sketches/checks";
    }

    @GetMapping ("/opportunity-map")
    public String getOpportunityMap (Model model) {
        /* TODO
            Карта (календарь) возможностей.
            Можно задать предпочтительное время работы
        */
        return null;
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
