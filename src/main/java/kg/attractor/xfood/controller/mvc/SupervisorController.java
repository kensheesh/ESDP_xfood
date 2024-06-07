package kg.attractor.xfood.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supervisor")
public class SupervisorController {

    @GetMapping("/opportunity-map")
    public String getOpportunityMap (Model model) {
        /* TODO
            Карта возможностей + назначение проверок + переход на редактирование шаблонов
        */
        return null;
    }

    @GetMapping("/profile")
    public String getProfile (Model model) {
        /* TODO
            Профиль
        */
        return null;
    }
}
