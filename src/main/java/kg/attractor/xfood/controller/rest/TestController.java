package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.service.OkHttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pp")
@RequiredArgsConstructor
public class TestController {
	private final OkHttpService okHttpService;
	
	@GetMapping()
	public ResponseEntity<?> test(@RequestParam String country) {
		return ResponseEntity.ok(okHttpService.getAllPizzerias(country));
	}
}
