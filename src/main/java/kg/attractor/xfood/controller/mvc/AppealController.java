package kg.attractor.xfood.controller.mvc;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.AppealSupervisorApproveDto;
import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

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


    @GetMapping("{id}")
    public String approveAppeal(@PathVariable Long id, Model model){
        model.addAttribute("appeal", appealService.getAppealById(id));
        return "appeals/approve";
    }

    @PostMapping("/approve")
    public String approveAppeal(AppealSupervisorApproveDto appeal) throws MessagingException, UnsupportedEncodingException {
        appealService.approve(appeal);
        return "redirect:/profile";
    }


}
