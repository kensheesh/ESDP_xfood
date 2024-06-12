package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.CheckListService;
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

    @GetMapping("/analytics")
    public String getAnalytics(Model model,
                               @RequestParam(name = "pizzeria", required = false) String pizzeria,
                               @RequestParam(name = "manager", required = false) String manager,
                               @RequestParam(name = "expert", required = false) String expert,
                               @RequestParam(name = "startDate", required = false) LocalDate startDate,
                               @RequestParam(name = "endDate", required = false) LocalDate endDate
    ) {
        model.addAttribute("checklists", checkListService.getAnalytics(
                pizzeria, manager, expert, startDate, endDate
        ));
        return "analytics/analytics";
    }
}
