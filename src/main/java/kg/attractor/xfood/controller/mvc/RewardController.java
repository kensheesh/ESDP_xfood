package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("reward")
@RequiredArgsConstructor
public class RewardController {

    private final UserService userService;

    @GetMapping()
    public String rewards(Model model) {
        User user = AuthParams.getPrincipal();
        model.addAttribute("expert", userService.getExpertReward(user.getUsername()));
        return "rewards/rewards";
    }
}
