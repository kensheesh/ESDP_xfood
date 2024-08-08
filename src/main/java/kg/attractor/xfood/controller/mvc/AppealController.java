package kg.attractor.xfood.controller.mvc;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.AppealSupervisorApproveDto;
import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.FileService;
import kg.attractor.xfood.service.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("appeals")
@RequiredArgsConstructor
public class AppealController {

    private final AppealService appealService;
    private final FileService fileService;

    @GetMapping
    public String getNewAppeals(@RequestParam (name = "p", defaultValue = "1") int page,
                                Model model) {
        var appeals = appealService.getAllByStatus(null, page);
        model.addAttribute("appeals", appeals);
        return "appeals/appeals";
    }
    
    @GetMapping("{id}")
    public String getFormAppeal(@PathVariable Long id, Model model) {
	    model.addAttribute("appeal", appealService.findById(id));
	    return "appeals/appeal";
    }

	@GetMapping("{id}/approve")
    public String approveAppeal(@PathVariable Long id, Model model){
        model.addAttribute("appeal", appealService.getAppealById(id));
        model.addAttribute("filenames", fileService.getPathsForAppealFiles(id));
        return "appeals/approve";
    }

    @PostMapping("/approve")
    public String approveAppeal(AppealSupervisorApproveDto appeal) throws MessagingException, UnsupportedEncodingException {
        appealService.approve(appeal);
        return "redirect:/profile";
    }
}