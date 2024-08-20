package kg.attractor.xfood.controller.rest;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.appeal.AppealDto;
import kg.attractor.xfood.dto.appeal.AppealListDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.dto.appeal.DataAppealDto;
import kg.attractor.xfood.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("restAppealController")
@RequiredArgsConstructor
@RequestMapping("api/appeal")
public class AppealController {
    private final AppealService appealService;

    @PostMapping(value = "")
    public ResponseEntity<Long> createAppeal(@RequestBody DataAppealDto data) {
        return ResponseEntity.ok(appealService.create(data));
    }

    @PostMapping("/comment")
    public ResponseEntity<Long> createAppealByComment(@RequestBody DataAppealDto data) {
        return ResponseEntity.ok(appealService.createByComment(data));
    }

    @PostMapping(value = "{id}", consumes = "multipart/form-data")
    public ResponseEntity<Object> updateAppeal(@PathVariable Long id, @ModelAttribute @Valid CreateAppealDto createAppealDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> response = new HashMap<>();
            response.put("errors", bindingResult.getFieldErrors());
            response.put("appealDto", createAppealDto);
            return ResponseEntity.badRequest().body(response);
        }
        appealService.update(createAppealDto, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("count")
    public ResponseEntity<Integer> getAmountOfNewAppeals() {
        Integer amount = appealService.getAmountOfNewAppeals();
        return ResponseEntity.ok(amount);
    }

    @GetMapping
    public ResponseEntity<Page<AppealListDto>> getAppealsByStatus(@RequestParam(name = "p", defaultValue = "1") int page,
                                                                  @RequestParam(name = "s", required = false) Boolean isAccepted,
                                                                  @RequestParam(name = "expertId", defaultValue = "0") Long expertId,
                                                                  @RequestParam(name = "pizzeriaId", defaultValue = "0") Long pizzeriaId) {
        Page<AppealListDto> appeals = appealService.getAllByStatus(isAccepted, page, pizzeriaId, expertId);
        return ResponseEntity.ok(appeals);
    }

    @GetMapping("accepted/{checklistId}/{criteriaId}")
    public ResponseEntity<List<AppealDto>> getHistoryAcceptedAppeals(@PathVariable Long checklistId,
                                                                     @PathVariable Long criteriaId)
    {
        return ResponseEntity.ok(appealService.getAcceptedAppeals(checklistId, criteriaId));
    }
}