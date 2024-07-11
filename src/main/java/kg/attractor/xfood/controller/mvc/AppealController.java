package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.dto.appeal.AppealSupervisorApproveDto;
import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("appeal")
@RequiredArgsConstructor
public class AppealController {

    private final CriteriaService criteriaService;
    private final CheckListService checkListService;
    private final AppealService appealService;

    @GetMapping("{id}")
    public String getFormAppeal(@PathVariable Long id, Model model) {
	    model.addAttribute("appeal", appealService.findById(id));
	    return "appeals/appeal";
    }
	
	
	@GetMapping("{id}/approve")
    public String approveAppeal(@PathVariable Long id, Model model){
        model.addAttribute("appeal", appealService.getAppealById(id));
        return "appeals/approve";
    }

    @PostMapping("/approve")
    public String approveAppeal(AppealSupervisorApproveDto appeal){
        appealService.approve(appeal);
        return "redirect:/profile";
    }


}
