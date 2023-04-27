package ryback.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ryback.api.data.RecipeIngredientRepository;
import ryback.api.model.RecipeIngredient;
import ryback.api.model.RecipeIngredientId;
import ryback.api.rest.RecipeIngredientRequestObject;

@RestController
@RequestMapping("/recipeIngredient")
public class RecipeIngredientController {
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @RequestMapping(method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:1234")
    public RecipeIngredientRequestObject delete(@RequestBody RecipeIngredientId id) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe Ingredient not found"));

        recipeIngredientRepository.delete(recipeIngredient);

        return new RecipeIngredientRequestObject(recipeIngredient);
    }
}
