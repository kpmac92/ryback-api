package ryback.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ryback.api.data.IngredientRepository;
import ryback.api.data.RecipeIngredientRepository;
import ryback.api.data.RecipeRepository;
import ryback.api.models.Ingredient;
import ryback.api.models.Recipe;
import ryback.api.models.RecipeIngredient;
import ryback.api.models.RecipeIngredientId;
import ryback.api.rest.RecipeIngredientRequestObject;
import ryback.api.rest.RecipeRequestObject;

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
    public Recipe create(@RequestBody RecipeRequestObject recipeRequest) {
        Recipe recipe = recipeRepository.save(new Recipe(recipeRequest));

        recipeRequest.getRecipeIngredients().forEach(ingredient -> saveRecipeIngredient(ingredient, recipe));

        return recipe;
    }

    @RequestMapping(value="delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:1234")
    public boolean delete(@RequestBody UUID recipeId) {
        recipeRepository.deleteById(recipeId);
        return true;
    }

    private void saveRecipeIngredient(RecipeIngredientRequestObject requestObject, Recipe recipe) {
        Ingredient ingredient;

        if(requestObject.getIngredientId() != null) {
            ingredient = ingredientRepository.findById(requestObject.getIngredientId()).get();

            Optional<RecipeIngredient> recipeIngredient = recipeIngredientRepository.findById(new RecipeIngredientId(recipe.getId(),
                            requestObject.getIngredientId()));

            if (recipeIngredient.isPresent()) {
                updateRecipeIngredient(recipeIngredient.get(), requestObject);
                return;
            }

        } else {
            Optional<Ingredient> ingredientResult = ingredientRepository.findByName(
                    requestObject.getIngredientName().trim().toLowerCase());

            ingredient = ingredientResult.orElseGet(() -> ingredientRepository.save(
                    new Ingredient(requestObject.getIngredientName().trim().toLowerCase(), requestObject.getDiscrete())));
        }

        RecipeIngredient recipeIngredientModel =
                new RecipeIngredient(requestObject.getPrimary(), recipe, ingredient,
                        requestObject.getAmountNumerator(), requestObject.getAmountDenominator());

        recipeIngredientRepository.save(recipeIngredientModel);
    }

    private void updateRecipeIngredient(RecipeIngredient recipeIngredient, RecipeIngredientRequestObject requestObject) {
        recipeIngredient.getAmount().setNumerator(requestObject.getAmountNumerator());
        recipeIngredient.getAmount().setDenominator(requestObject.getAmountDenominator());
        recipeIngredient.setIsPrimary(requestObject.getPrimary());
        recipeIngredientRepository.save(recipeIngredient);
    }
}
