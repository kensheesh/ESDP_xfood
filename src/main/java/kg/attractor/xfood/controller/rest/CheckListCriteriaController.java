package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaDto;
import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("CheckListRestController")
@RequiredArgsConstructor
@RequestMapping("api/checklist/criteria")
public class CheckListCriteriaController {

    private final CheckListCriteriaService checkListCriteriaService;

    @PostMapping("create/wow")
    public ResponseEntity<CheckListCriteriaDto> createNewWowFactor(@RequestBody SaveCriteriaDto saveCriteriaDto) {
        return ResponseEntity.ok(checkListCriteriaService.createWowFactor(saveCriteriaDto));
    }

    @DeleteMapping("wow/delete/{id}")
    public HttpStatus deleteWowFactor(@PathVariable(name = "id") Long id, @RequestParam(name = "checkListId") Long checkListId) {
        checkListCriteriaService.deleteWowFactor(id, checkListId);
        return HttpStatus.OK;
    }

    @PostMapping("create/crit")
    public ResponseEntity<CheckListCriteriaDto> createNewCritFactor(
            @RequestParam(name = "description") String description,
            @RequestBody SaveCriteriaDto saveCriteriaDto) {
        try{
            long criteriaId = Long.parseLong(description);
            saveCriteriaDto.setCriteriaId(criteriaId);
            return ResponseEntity.ok(checkListCriteriaService.createCritFactor(saveCriteriaDto, null));
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(checkListCriteriaService.createCritFactor(saveCriteriaDto, description));
        }

    }
}
