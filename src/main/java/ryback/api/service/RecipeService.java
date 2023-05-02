package ryback.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ryback.api.data.IngredientRepository;
import ryback.api.data.RecipeIngredientRepository;
import ryback.api.data.RecipeRepository;
import ryback.api.model.Ingredient;
import ryback.api.model.Recipe;
import ryback.api.model.RecipeIngredient;
import ryback.api.model.RecipeIngredientId;
import ryback.api.rest.RecipeIngredientRequestObject;
import ryback.api.rest.RecipeRequestObject;

import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    public RecipeRequestObject saveRecipe(RecipeRequestObject requestObject) {
        Recipe recipe = recipeRepository.save(new Recipe(requestObject));

        requestObject.getRecipeIngredients().forEach(ingredient -> saveRecipeIngredient(ingredient, recipe));

        return new RecipeRequestObject(recipe, false);
    }

    private void saveRecipeIngredient(RecipeIngredientRequestObject requestObject, Recipe recipe) {
        Ingredient ingredient;

        if(requestObject.getIngredientId() != null) {
            ingredient = ingredientRepository.findById(requestObject.getIngredientId()).get();

            Optional<RecipeIngredient> recipeIngredient = recipeIngredientRepository.findById(new RecipeIngredientId(recipe.getId(),
                    requestObject.getIngredientId()));

            if (recipeIngredient.isPresent()) {
                RecipeIngredient recipeIngredientModel = recipeIngredient.get();

                if(recipeIngredientModel.getIngredient().getName().equals(requestObject.getIngredientName().trim().toLowerCase())) {
                    updateRecipeIngredient(recipeIngredient.get(), requestObject);
                    return;
                } else {
                    recipeIngredientRepository.delete(recipeIngredientModel);
                    ingredient = findOrCreateIngredient(requestObject);
                }
            }

        } else {
            ingredient = findOrCreateIngredient(requestObject);
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

    private Ingredient findOrCreateIngredient(RecipeIngredientRequestObject requestObject) {
        Optional<Ingredient> ingredientResult = ingredientRepository.findByName(
                requestObject.getIngredientName().trim().toLowerCase());

        return ingredientResult.orElseGet(() -> ingredientRepository.save(
                new Ingredient(requestObject.getIngredientName().trim().toLowerCase(), requestObject.getDiscrete())));
    }

}
