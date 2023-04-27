package ryback.api.rest;

import ryback.api.model.Recipe;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeRequestObject {
    private UUID id;

    private String name;
    private String description;
    private Integer time;
    private List<RecipeIngredientRequestObject> recipeIngredients;

    public RecipeRequestObject() {
    }

    public RecipeRequestObject(Recipe recipe, boolean includeIngredients) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.time = recipe.getTime();
        if(includeIngredients) {
            this.recipeIngredients = recipe.getRecipeIngredients()
                    .stream()
                    .map(RecipeIngredientRequestObject::new)
                    .collect(Collectors.toList());
        }

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public List<RecipeIngredientRequestObject> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredientRequestObject> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
