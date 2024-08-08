package kg.attractor.xfood.controller.rest;


import kg.attractor.xfood.service.CheckTypeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("checkTypeControllerRest")
@RequiredArgsConstructor
@RequestMapping("/api/check-type/")
public class CheckTypeController {
    private static final Logger log = LoggerFactory.getLogger(CheckTypeController.class);
    private final CheckTypeService checkTypeService;

//    @GetMapping("/{id}")
//    public ResponseEntity<Integer> getTypeMaxTotalValue(@PathVariable Long id) {
//        log.info("getTypeMaxTotalValue {}", checkTypeService.getTypeById(id));
//        return ResponseEntity.ok(checkTypeService.getTypeById(id));
//    }

}
