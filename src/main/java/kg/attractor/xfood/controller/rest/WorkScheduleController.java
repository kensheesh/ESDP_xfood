package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.workSchedule.WeeklyScheduleShowDto;
import kg.attractor.xfood.service.impl.WorkScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers-work-schedule")
public class WorkScheduleController {
    private final WorkScheduleServiceImpl workScheduleService;

//    ROLE: SUPERVISOR
    @GetMapping("pizzeria/{id}")
    public ResponseEntity<List<WeeklyScheduleShowDto>> getManagersSchedules (
            @PathVariable (name = "id") Long pizzeriaId
    ) {
        List<WeeklyScheduleShowDto> dtos = workScheduleService.getWeeklySchedulesByPizzeriaId(pizzeriaId);

        return ResponseEntity.ok(dtos);
    }

}
