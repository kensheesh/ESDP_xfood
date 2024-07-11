package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("appeal")
@RequiredArgsConstructor
public class AppealController {

    private final CriteriaService criteriaService;
    private final CheckListService checkListService;
    private final AppealService appealService;

    @GetMapping("")
    public String getFormAppeal(@RequestParam(name = "criteriaId") Long criteriaId,
                                @RequestParam(name = "checkListId") String checkListId,
                                Model model){
        model.addAttribute("criteria", criteriaService.getCriteriaById(criteriaId));
        model.addAttribute("checkList", checkListService.getCheckListById(checkListId));

        return "appeals/appeal";
    }


    @GetMapping("{id}/approve")
    public String approveAppeal(@PathVariable Long id, Model model){
        model.addAttribute("appeal", appealService.getAppealById(id));
        return "appeals/approve";
    }
}
