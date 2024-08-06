package kg.attractor.xfood.controller.rest;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.auth.RegisterUserDto;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class UserManagementController {
	
	private final UserService userService;
	
	@PostMapping("user/create")
	@PreAuthorize("hasAnyAuthority('admin:create','supervisor:create')")
	public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserDto dto) {
		if (! userService.isUserExist(dto.getEmail())) {
			userService.register(dto);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
		}
	}
	
}
