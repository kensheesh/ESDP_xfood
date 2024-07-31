package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/users";
    }
}
