package kg.attractor.xfood.controller.rest;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.appeal.AppealListDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.dto.appeal.DataAppealDto;
import kg.attractor.xfood.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @PostMapping(value = "{id}", consumes = "multipart/form-data")
    public HttpStatus updateAppeal(@PathVariable Long id, @ModelAttribute @Valid CreateAppealDto createAppealDto) {
        appealService.update(createAppealDto, id);
        return HttpStatus.OK;
    }

    @GetMapping("count")
    public ResponseEntity<Integer> getAmountOfNewAppeals() {
        Integer amount = appealService.getAmountOfNewAppeals();
        return ResponseEntity.ok(amount);
    }

    @GetMapping
    public ResponseEntity<Page<AppealListDto>> getAppealsByStatus(@RequestParam(name = "p", defaultValue = "1") int page,
                                                                  @RequestParam(name = "s", required = false) Boolean isAccepted) {
        Page<AppealListDto> appeals = appealService.getAllByStatus(isAccepted, page);
        return ResponseEntity.ok(appeals);
    }
}