package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController("expertControllerRest")
@RequiredArgsConstructor
@RequestMapping("/api/experts")
public class ExpertController {
    private final CheckListService checkListService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllExperts() {
        List<UserDto> users = userService.getAllExperts();
        return ResponseEntity.ok(users);
    }

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

    //ROlE EXPERT
    @GetMapping("checks")
    public ResponseEntity<?> getCheckLists(@RequestParam(name = "status", defaultValue = "in_progress") String status) {
        List<ChecklistMiniExpertShowDto> checkLists = checkListService.getUsersChecklists(AuthParams.getPrincipal().getUsername(), Status.getStatusEnum(status));
        return ResponseEntity.ok(checkLists);
    }
}
