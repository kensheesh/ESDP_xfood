package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("wow/delete/{id}")
    public HttpStatus deleteWowFactor(@PathVariable(name = "id") Long id, @RequestParam(name = "checkListId") Long checkListId) {
        checkListCriteriaService.deleteWowFactor(id, checkListId);
        return HttpStatus.OK;
    }
}
