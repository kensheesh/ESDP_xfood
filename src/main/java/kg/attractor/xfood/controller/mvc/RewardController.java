package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.enums.Role;
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
@RequestMapping("reward")
@RequiredArgsConstructor
public class RewardController {

    @Lazy
    private final UserService userService;

    @GetMapping()
    public String rewards(Model model,
                          @RequestParam(name = "pizzeria", defaultValue = "default", required = false) String pizzeria,
                          @RequestParam(name = "expert", defaultValue = "default", required = false) String expert,
                          @RequestParam(name = "startDate", required = false) LocalDate startDate,
                          @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        Role role = userService.getUserDto().getRole();
        if (role.equals(Role.SUPERVISOR)) {
            model.addAttribute("experts", userService.getRewards(pizzeria, expert, startDate, endDate));
        }
        if(role.equals(Role.EXPERT)) {
            model.addAttribute("expert", userService.getExpertReward(startDate, endDate));
        }
        return "rewards/rewards";
    }
}
