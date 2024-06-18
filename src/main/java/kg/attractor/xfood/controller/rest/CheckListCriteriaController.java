package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("CheckListRestController")
@RequiredArgsConstructor
@RequestMapping("api/checklist/criteria")
public class CheckListCriteriaController {

    private final CheckListCriteriaService checkListCriteriaService;

    @PostMapping("create/wow")
    public HttpStatus createNewWowFactor(@RequestBody SaveCriteriaDto saveCriteriaDto) {
        checkListCriteriaService.createWowFactor(saveCriteriaDto);
        return HttpStatus.OK;
    }
}
