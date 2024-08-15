package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.service.RewardService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("rewards")
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;
    @Lazy
    private final UserService userService;

    @GetMapping()
    public String rewards(Model model,
                          @RequestParam(name = "pizzeria", defaultValue = "default", required = false) String pizzeria,
                          @RequestParam(name = "expert", defaultValue = "default", required = false) String expert,
                          @RequestParam(name = "startDate", required = false) LocalDate startDate,
                          @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        Role role = userService.getUserDto().getRole();
        if (role.equals(Role.SUPERVISOR) || role.equals(Role.ADMIN)) {
            model.addAttribute("experts", rewardService.getRewards(pizzeria, expert, startDate, endDate));
        }
        if(role.equals(Role.EXPERT)) {
            String expertEmail = userService.getUserDto().getEmail();
            model.addAttribute("expert", rewardService.getExpertReward(expertEmail, null, startDate, endDate));
        }

        model.addAttribute("currentExpert", expert);
        model.addAttribute("currentPizzeria", pizzeria);
        model.addAttribute("currentStartDate", startDate);
        model.addAttribute("currentEndDate", endDate);
        return "rewards/rewards";
    }
}
