package ryback.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:1234")
    public List<Recipe> get() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Pancakes",
                "The classic wholesome breakfast. Serve with butter, syrup, chocolate chips, blueberries, or whatever else you want!",
                30));
        recipes.add(new Recipe("Creamy Cajun Pasta", "it's creamy, it's cajun, it's pasta.", 60));
        recipes.add(new Recipe("Honey Sesame Chicken", "tasty asian chicken with rice and veggies", 90));
        return recipes;
    }
}
