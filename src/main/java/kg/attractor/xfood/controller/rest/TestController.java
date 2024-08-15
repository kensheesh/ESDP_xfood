package kg.attractor.xfood.controller.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.service.BearerTokenService;
import kg.attractor.xfood.service.OAuthService;
import kg.attractor.xfood.service.OkHttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TestController {
	
	private final OkHttpService okHttpService;
	private final OAuthService oAuthService;
	private final BearerTokenService bearerTokenService;
	
	@GetMapping("/oauth/callback")
	public String callback(@RequestParam() String code, @RequestParam() String scope) {
		
		oAuthService.getAccessToken(code, scope, AuthParams.getPrincipal().getUsername());
		return "redirect:/analytics";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
	@PostMapping("/bearer")
	public ResponseEntity<?> setBearer(
			@RequestParam @Valid @NotBlank String bearer,
			@RequestParam(required = false) Long lifeTime) {
		
		if (AuthParams.getAuth().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			bearerTokenService.setBearerForSupervisors(bearer, lifeTime);
		} else {
			bearerTokenService.setBearer(AuthParams.getPrincipal().getUsername(), bearer, lifeTime);
		}
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "redirect:/" + oAuthService.getAuthorizationUrl();
	}
	
	
}
//	@GetMapping()
//	public ResponseEntity<?> test(@RequestParam String country) {
//		return ResponseEntity.ok(okHttpService.getAllPizzeriasByCountry(country));
//	}
//
//	@PostMapping()
//	public ResponseEntity<?> testPost(@RequestParam List<String> country) {
//		okHttpService.rewritePizzeriasToRedis(country);
//		return ResponseEntity.ok().build();
//	}
//
//	@GetMapping("/{name}")
//	public ResponseEntity<?> testName(@PathVariable String name) {
//		return ResponseEntity.ok(okHttpService.getPizzeriasByMatch(name));
//	}
//
//	@GetMapping("/pizz/{uuid}/")
//	public ResponseEntity<?> getschedule(@PathVariable String uuid) {
//		return ResponseEntity.ok(okHttpService.getPizzeriaStaff("ru",uuid));
//	}