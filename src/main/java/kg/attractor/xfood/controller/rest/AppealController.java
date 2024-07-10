package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/appeal")
public class AppealController {
    private final AppealService appealService;


    @PostMapping(value = "", consumes = "multipart/form-data")
    public HttpStatus createAppeal(@RequestBody CreateAppealDto createDto) {
        appealService.create(createDto);
        return HttpStatus.OK;
    }
}
