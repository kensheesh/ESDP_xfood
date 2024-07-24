package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/statistics")
public class StatisticsController {
    private final UserService userService;
    private final CheckListService checkListService;


    @GetMapping
    private String statistics(@RequestParam(name = "from", required = false) LocalDate from, @RequestParam(name = "to", required = false)LocalDate to, Model model){
      /*  if (from != null && to != null) {
            log.info("from: {}, to: {}", from, to);
            model.addAttribute("statistics", checkListService.getStatistics(from, to));
        }*/
        model.addAttribute("experts", userService.getAllExperts());
        return "analytics/statistics";
    }
}
