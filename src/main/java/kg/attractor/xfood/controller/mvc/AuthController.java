package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {
	
	private final UserService userService;
	
	@GetMapping("login")
	String login() {
		return "/auth/login";
	}
}
