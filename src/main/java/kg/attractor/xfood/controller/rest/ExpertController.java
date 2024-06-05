package kg.attractor.xfood.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/experts")
public class ExpertController {

    // ROLE: SUPERVISOR
    @GetMapping("/availability")
    public ResponseEntity<Map<String, List<?>>> getAllByAvailability(
            @RequestParam(name = "datetime") LocalDateTime checkTime
    ) {
        /*    TODO:
                 Метод для получения списка
                 ДОСТУПНЫХ на данное время и
                 ОСТАЛЬНЫХ экспертов при назначений
                 новой проверки.
                 В шаблоне отображаются сначала доступные эксперты
                 на выбранное время, затем остальные.
                 Н-р:
                 |
                    Время проверки: 19:00 - 19:50
                 |
                    ---- Доступны ---
                    Иванов Иван
                    Никитин Никита
                    --- Остальные ---
                    Темиров Темир
                 |
                 --------------------
                 LocalDateTime подходящий тип?
                 --------------------
                 Структурно:
                 {
                      "availableExperts": [
                          {},
                          {}
                      ],
                      "others": [
                          {},
                          {}
                      ]
                 }
        */
        return null;
    }
}
