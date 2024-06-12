package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.checklist.ChecklistExpertShowDto;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TestCont {
	
	private final CheckListService checkListService;
	
	
	@GetMapping("checklist")
	public ResponseEntity<?> getCheckLists(@RequestParam(name = "status", defaultValue = "in_progress") String status) {
		List<ChecklistExpertShowDto> checkLists = checkListService.getUsersChecklists(AuthParams.getPrincipal().getUsername(), status);
		return ResponseEntity.ok(checkLists);
	}
	
}
