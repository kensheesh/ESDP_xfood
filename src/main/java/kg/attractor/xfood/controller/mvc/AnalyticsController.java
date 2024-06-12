package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public String getAnalytics(Model model,
                               @RequestParam(name = "pizzeria", defaultValue = "default", required = false) String pizzeria,
                               @RequestParam(name = "manager", defaultValue = "default", required = false) String manager,
                               @RequestParam(name = "expert", defaultValue = "default", required = false) String expert,
                               @RequestParam(name = "startDate", required = false) LocalDate startDate,
                               @RequestParam(name = "endDate", required = false) LocalDate endDate
    ) {
        model.addAttribute("checklists", checkListService.getAnalytics(
                pizzeria, manager, expert, startDate, endDate
        ));
        model.addAttribute("user", userService.getUserDto());

        // Добавляем текущие значения параметров в модель
        model.addAttribute("currentPizzeria", pizzeria);
        model.addAttribute("currentManager", manager);
        model.addAttribute("currentExpert", expert);
        model.addAttribute("currentStartDate", startDate);
        model.addAttribute("currentEndDate", endDate);
        return "analytics/analytics";
    }
}
