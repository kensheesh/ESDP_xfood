package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.appeal.DataAppealDto;
import kg.attractor.xfood.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("restAppealController")
@RequiredArgsConstructor
@RequestMapping("api/appeal")
public class AppealController {
    private final AppealService appealService;


    @PostMapping(value = "")
    public ResponseEntity<Long> createAppeal(@RequestBody DataAppealDto data) {
        return ResponseEntity.ok(appealService.create(data));
    }
}
