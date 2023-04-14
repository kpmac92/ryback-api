package ryback.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:1234")
    public List<Recipe> get() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @RequestMapping(value="create", produces = MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:1234")
    public Recipe create(@RequestBody Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    @RequestMapping(value="delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:1234")
    public boolean delete(@RequestBody UUID recipeId) {
        recipeRepository.deleteById(recipeId);
        return true;
    }
}
