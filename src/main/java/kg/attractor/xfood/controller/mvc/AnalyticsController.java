package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class AnalyticsController {
    private final CheckListService checkListService;
    private final UserService userService;

    @GetMapping("/analytics")
    public String getAnalytics(Model model, Authentication authentication,
                               @RequestParam(name = "pizzeria", required = false, defaultValue = "default") String pizzeria,
                               @RequestParam(name = "manager", required = false) String manager,
                               @RequestParam(name = "expert", required = false) String expert,
                               @RequestParam(name = "startDate", required = false) LocalDate startDate,
                               @RequestParam(name = "endDate", required = false) LocalDate endDate
    ) {
        System.out.println();
        System.out.println(pizzeria);
        System.out.println();
        model.addAttribute("checklists", checkListService.getAnalytics(
                pizzeria, manager, expert, startDate, endDate
        ));
        model.addAttribute("user", userService.getUserDto());
        return "analytics/analytics";
    }
}
