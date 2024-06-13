package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checks")
public class CheckListController {
    private final CheckListService checkListService;

    // ROLE: SUPERVISOR
    @GetMapping("/create")
    public String create () {
        return null;
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
        ChecklistShowDto checkListDto = checkListService.getCheckListById(checkListId);
        model.addAttribute("checkList", checkListDto);

        return "check_list/check_list";
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
