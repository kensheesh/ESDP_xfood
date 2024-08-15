package kg.attractor.xfood.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {

    @GetMapping ("/profile/history")
    public String getHistory (Model model) {
        return null;
        /* TODO
            История проверок эксперта.
            Можно перейти из страницы профиля
        */
    }
}
