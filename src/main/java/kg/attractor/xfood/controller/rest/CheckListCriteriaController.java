package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.service.CheckListCriteriaService;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("CheckListRestController")
@RequiredArgsConstructor
@RequestMapping("api/checklist/criteria")
public class CheckListCriteriaController {

    private final CheckListCriteriaService checkListCriteriaService;
    private final CheckListService checkListService;

    @PostMapping("create/wow")
    public ResponseEntity<CheckListCriteriaDto> createNewWowFactor(@RequestBody SaveCriteriaDto saveCriteriaDto) {
        return ResponseEntity.ok(checkListCriteriaService.createNewFactor(saveCriteriaDto));
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus deleteWowFactor(@PathVariable(name = "id") Long id, @RequestParam(name = "checkListId") Long checkListId) {
        checkListCriteriaService.deleteFactor(id, checkListId);
        return HttpStatus.OK;
    }

    @PostMapping("create/crit")
    public ResponseEntity<CheckListCriteriaDto> createNewCritFactor(
            @RequestParam(name = "description") String description,
            @RequestBody SaveCriteriaDto saveCriteriaDto) {
        try{
            long criteriaId = Long.parseLong(description);
            saveCriteriaDto.setCriteriaId(criteriaId);
            return ResponseEntity.ok(checkListCriteriaService.createNewFactor(saveCriteriaDto));
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(checkListCriteriaService.createCritFactor(saveCriteriaDto, description));
        }
    }

    @GetMapping("percentage/{id}")
    public ResponseEntity<Integer> getPercentageByCheckList(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(checkListService.getPercentageById(id));
    }

    @GetMapping("{uuid}")
    public ResponseEntity<List<CriteriaExpertShowDto>> getCriteriaByCheckListId(@PathVariable(name = "uuid") String checkListUUID) {
        return ResponseEntity.ok(checkListService.getCheckListById(checkListUUID).getCriteria());
    }

    @PostMapping("save")
    public String saveCriteriaForCheckList(@RequestBody List<SaveCriteriaDto> saveCriteriaDto) {
        checkListCriteriaService.save(saveCriteriaDto);
        return "redirect:";
    }

    @GetMapping("{checkId}/{criteriaId}")
    public ResponseEntity<CriteriaSupervisorShowDto> getCheckListCriteria(@PathVariable(name = "checkId") Long checkId, @PathVariable Long criteriaId) {
        return ResponseEntity.ok(checkListCriteriaService.getByCheckIdAndCriteriaId(checkId, criteriaId));
    }
}
