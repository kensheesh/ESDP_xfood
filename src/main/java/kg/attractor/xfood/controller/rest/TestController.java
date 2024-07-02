package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.service.OkHttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pp")
@RequiredArgsConstructor
public class TestController {
	private final OkHttpService okHttpService;
	
//	@GetMapping()
//	public ResponseEntity<?> test(@RequestParam String country) {
//		return ResponseEntity.ok(okHttpService.getAllPizzeriasByCountry(country));
//	}
//
	@PostMapping()
	public ResponseEntity<?> testPost(@RequestParam List<String> country) {
		okHttpService.rewritePizzeriasToRedis(country);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<?> testName(@PathVariable String name) {
		return ResponseEntity.ok(okHttpService.getPizzeriasByMatch(name));
	}
	
	@GetMapping("/pizz/{uuid}/")
	public ResponseEntity<?> getschedule(@PathVariable String uuid) {
		return ResponseEntity.ok(okHttpService.getPizzeriaStaff(uuid,"ru"));
	}
}
