package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.service.AdminService;
import kg.attractor.xfood.service.UserService;
import kg.attractor.xfood.service.impl.DtoBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("restAdminController")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final DtoBuilder dtoBuilder;

    @DeleteMapping("/user/{id}")
    public HttpStatus userDelete(@PathVariable Long id) {
        adminService.deleteUser(id);
        return HttpStatus.OK;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(dtoBuilder.buildUserDto(userService.findById(id)));
    }
}
