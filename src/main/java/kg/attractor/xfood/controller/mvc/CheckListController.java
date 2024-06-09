package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.checks.CheckListDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("check-list")
public class CheckListController {

    private final CheckListService checkListService;

    @GetMapping("{id}")
    public String fillCheckList(@PathVariable Long id, Model model) {
        CheckListDto checkList = checkListService.getCheckListById(id);
        model.addAttribute("checkList", checkList);
        return "/check_list/check_list";
    }


}
