package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.CheckTypeService;
import kg.attractor.xfood.service.CriteriaService;
import kg.attractor.xfood.service.impl.CheckTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checks")
public class CheckListController {
    private final CheckListService checkListService;
    private final CheckTypeService checkTypeService;
    private final CriteriaService criteriaService;

    // ROLE: SUPERVISOR
    @GetMapping("/create")
    public String create (@RequestParam(name = "type", required = false)String type, Model model) {
        model.addAttribute("criterion", criteriaService.getCriterion(type));
        model.addAttribute("types",checkTypeService.getTypes());
        return "checklist/create";
    }

    // ROLE: SUPERVISOR
    @PostMapping("/create")
    public String create (BindingResult result, Model model) {
        return null;
        // TODO назначение проверки
    }

//    // ROLE: ADMIN
//    @GetMapping ("/{id}/edit")
//    public String edit (@PathVariable (name="id") Long checkListId, Model model) {
//        return null;
//    }
//
//    // ROLE: ADMIN
//    @PostMapping ("/{id}/edit")
//    public String edit (@PathVariable (name="id") Long checkListId, BindingResult result, Model model) {
//        return null;
//    }

    // ROLE: EXPERT
    @GetMapping ("/{id}/check")
    public String check (@PathVariable (name="id") Long checkListId, Model model) {
         /* TODO:
             Проверка
        */

        return null;
    }

    // ROLE: EXPERT
    @PostMapping ("/{id}/check")
    public String check (@PathVariable (name="id") Long checkListId, BindingResult result, Model model) {
        /* TODO:
            Подтверждение проверки экспертом
        */

        return null;
    }

    // ROLE: SUPERVISOR, ADMIN
    @GetMapping ("/{id}/change")
    public String changeResult (@PathVariable (name="id") Long checkListId, Model model) {
        /* TODO:
            Изменение результата проверки *после
            подтверждения экспертом
        */

        return null;
    }

    // ROLE: SUPERVISOR, ADMIN
    @PostMapping ("/{id}/change")
    public String changeResult (@PathVariable (name="id") Long checkListId, BindingResult result, Model model) {
        /* TODO:
            Изменение результата проверки *после
            подтверждения экспертом
        */

        return null;
    }


    @GetMapping ("/{id}/result")
    public String getResult (@PathVariable (name = "id") Long checkListId, Model model) {
        model.addAttribute("checkList", checkListService.getResult(checkListId));

        return "checklist/result";
    }

}
