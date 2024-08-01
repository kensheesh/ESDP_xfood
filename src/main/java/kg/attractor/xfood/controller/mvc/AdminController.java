package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("users")
    public String getUsers(Model model,
                           @RequestParam(name = "role", defaultValue = "default", required = false) String role,
                           @RequestParam(name = "page", defaultValue = "0") String page) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 3);
        Page<UserDto> userPage = userService.getAllUsers(role, pageable);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentRole", role);
        model.addAttribute("currentPage", Integer.parseInt(page));
        return "users/users";
    }
}
