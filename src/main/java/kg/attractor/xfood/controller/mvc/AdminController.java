package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
