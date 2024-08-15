package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERVISOR','EXPERT','ADMIN')")
public class AnalyticsController {

    private final CheckListService checkListService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('supervisor:read','expert:read','admin:read')")
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

        model.addAttribute("currentPizzeria", pizzeria);
        model.addAttribute("currentManager", manager);
        model.addAttribute("currentExpert", expert);
        model.addAttribute("currentStartDate", LocalDate.now());
        model.addAttribute("currentEndDate", LocalDate.now());
        model.addAttribute("currentStartDate", startDate);
        model.addAttribute("currentEndDate", endDate);
        if (AuthParams.getPrincipal().getAuthorities().contains(Role.EXPERT)) {
            model.addAttribute("checksCount", checkListService.getAmountOfNewChecks());
        }

        return "analytics/analytics";
    }


}