package kg.attractor.xfood.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @GetMapping("pizzerias-management")
    public String getPizzaManagement(Model model) {
        return "pizzerias/pizzeria_management";
    }
}
