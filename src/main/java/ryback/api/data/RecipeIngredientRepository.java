package ryback.api.data;

import org.springframework.data.repository.CrudRepository;
import ryback.api.models.RecipeIngredient;
import ryback.api.models.RecipeIngredientId;

public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, RecipeIngredientId> {
}
