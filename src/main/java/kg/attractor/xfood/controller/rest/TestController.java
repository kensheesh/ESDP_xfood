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
		return ResponseEntity.ok(okHttpService.getPizzeriaStaff("ru",uuid));
	}
	
	@PostMapping("/bbb")
	public ResponseEntity<?> testNamePost() {
		String bb="b2d05103bcfe3f35cd399f35e34fd07878f5b87f68daad2a3f4e768aa8b528bd";
		okHttpService.setBearerForSupervisors(bb,500L);
		return ResponseEntity.ok().build();
	}
	
}
