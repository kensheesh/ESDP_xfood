package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@Slf4j
@Component
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {
	
	private final UserService userService;
	
	@GetMapping("login")
	String login(@RequestParam(defaultValue = "false", required = false) Boolean error, Model model) {
		if (error.equals(Boolean.TRUE)) {
			model.addAttribute("error", "Invalid Username or Password");
		}
		return "/auth/login";
		//TODO LOGIN TEMPLATE/PAGE
	}
}
