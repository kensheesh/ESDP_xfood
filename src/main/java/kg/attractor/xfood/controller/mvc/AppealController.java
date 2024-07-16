package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("appeals")
@RequiredArgsConstructor
public class AppealController {

    private final AppealService appealService;

    @GetMapping("{id}")
    public String getFormAppeal(@PathVariable Long id, Model model){
        model.addAttribute("appeal", appealService.findById(id));
        return "appeals/appeal";
    }

    @GetMapping
    public String getNewAppeals(@RequestParam (name = "p", defaultValue = "1") int page,
                                Model model) {
        var appeals = appealService.getAllByStatus(null, page);
        model.addAttribute("appeals", appeals);
        return "appeals/appeals";
    }
}
