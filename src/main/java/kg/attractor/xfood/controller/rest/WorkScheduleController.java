package kg.attractor.xfood.controller.rest;

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

    /* TODO
        Получение расписания всех менеджеров
        конкретной пиццерии для отображения
        календаря (списка) при назначении проверки */
    @GetMapping("pizzeria/{id}")
    public ResponseEntity<List<?>> getManagersSchedules (
            @PathVariable (name = "id") Long pizzeriaId
    ) {
        return null;
    }

}
