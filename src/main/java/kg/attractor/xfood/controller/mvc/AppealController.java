package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("appeal")
@RequiredArgsConstructor
public class AppealController {

    private final CriteriaService criteriaService;
    private final CheckListService checkListService;

    @GetMapping("")
    public String getFormAppeal(@RequestParam(name = "criteriaId") Long criteriaId,
                                @RequestParam(name = "checkListId") String checkListId,
                                Model model){
        model.addAttribute("criteria", criteriaService.getCriteriaById(criteriaId));
        model.addAttribute("checkList", checkListService.getCheckListById(checkListId));

        return "appeals/appeal";
    }
}
