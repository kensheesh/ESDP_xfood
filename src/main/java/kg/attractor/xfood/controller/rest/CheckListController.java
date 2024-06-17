package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kg.attractor.xfood.enums.Status.DONE;

@RestController("checkListControllerRest")
@RequiredArgsConstructor
@RequestMapping("api/checks")
public class CheckListController {
    private final CheckListService checkListService;

    @GetMapping("{id}")
    public ResponseEntity<String> getUuidLinkById(@PathVariable(name = "id") Long id) {
        try {
            String uuidLink = checkListService.getResult(id).getUuidLink();
            return ResponseEntity.ok(uuidLink);
        } catch (Exception e) {
            System.err.println("Error retrieving UUID link: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving UUID link");
        }
    }


    @PostMapping("post/{id}")
    public HttpStatus postCheck(@PathVariable(name = "id") Long id) {
        checkListService.updateCheckStatusCheckList(id);
        return HttpStatus.OK;
    }



}
