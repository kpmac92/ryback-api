package ryback.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @RequestMapping("/")
    public String get(){
        return "here's an ingredient!";
    }
}
