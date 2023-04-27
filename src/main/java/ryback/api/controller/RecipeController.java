package ryback.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ryback.api.data.IngredientRepository;
import ryback.api.data.RecipeIngredientRepository;
import ryback.api.data.RecipeRepository;
import ryback.api.model.Ingredient;
import ryback.api.model.Recipe;
import ryback.api.model.RecipeIngredient;
import ryback.api.model.RecipeIngredientId;
import ryback.api.rest.RecipeIngredientRequestObject;
import ryback.api.rest.RecipeRequestObject;
import ryback.api.service.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:1234")
    public List<Recipe> get() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:1234")
    public RecipeRequestObject get(@PathVariable UUID id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return new RecipeRequestObject(recipe.get(), true);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
    }

    @RequestMapping(value="create", produces = MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:1234")
    public RecipeRequestObject create(@RequestBody RecipeRequestObject recipeRequest) {
        RecipeRequestObject savedRecipe = recipeService.saveRecipe(recipeRequest);

        return savedRecipe;
    }

    @RequestMapping(value="delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:1234")
    public boolean delete(@RequestBody UUID recipeId) {
        recipeRepository.deleteById(recipeId);
        return true;
    }
}
