package kg.attractor.xfood.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AnalyticsController {

//    AUTHORIZED
    @GetMapping("/analytics")
    public String get (/* TODO @RequestParams*/) {

        return "analytics/analytics";
    }
}
