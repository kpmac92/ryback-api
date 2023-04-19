package ryback.api.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class RecipeIngredientId implements Serializable {
    private UUID recipeId;
    private UUID ingredientId;

    public RecipeIngredientId() {
    }

    public RecipeIngredientId(UUID recipeId, UUID ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }

    public void setIngredientId(UUID ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeIngredientId that = (RecipeIngredientId) o;

        if (!Objects.equals(recipeId, that.recipeId)) return false;
        return Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        int result = recipeId != null ? recipeId.hashCode() : 0;
        result = 31 * result + (ingredientId != null ? ingredientId.hashCode() : 0);
        return result;
    }
}
