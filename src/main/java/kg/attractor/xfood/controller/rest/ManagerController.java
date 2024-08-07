package kg.attractor.xfood.controller.rest;


import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read', 'expert:read')")
    public ResponseEntity<List<ManagerDto>> getManagers() {
        List<ManagerDto> managers = managerService.getAllManagers();
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/asc")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public ResponseEntity<List<ManagerDto>> getManagersAscBySurname() {
        List<ManagerDto> managers = managerService.getAllManagersAscBySurname();
        return ResponseEntity.ok(managers);
    }

}
