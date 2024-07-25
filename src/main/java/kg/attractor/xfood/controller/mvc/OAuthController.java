package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.util.UriEncoder;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {
	
	private final OAuthService oAuthService;
	
	@GetMapping("/login")
	public String login() {
		String authorizationUrl = oAuthService.getAuthorizationUrl();
		System.out.println(UriEncoder.encode(authorizationUrl));
		return "redirect:" + UriEncoder.encode(authorizationUrl);
	}
	
	@GetMapping("/callback")
	public String callback(@RequestParam() String code, @RequestParam() String scope) {
		oAuthService.getAccessToken(code, scope, AuthParams.getPrincipal().getUsername());
		return "redirect:/analytics";
	}
	
}
