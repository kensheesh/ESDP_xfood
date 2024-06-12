package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("criteria")
@RequiredArgsConstructor
public class CriteriaController {
    private final CheckListCriteriaService checkListCriteriaService;

    @PostMapping("save")
    public String saveCriteriaForCheckList(@RequestBody List<SaveCriteriaDto> saveCriteriaDto) {
        checkListCriteriaService.save(saveCriteriaDto);
        return "redirect:";
    }
}
