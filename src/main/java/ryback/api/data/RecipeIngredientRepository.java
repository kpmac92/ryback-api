package ryback.api.data;

import org.springframework.data.repository.CrudRepository;
import ryback.api.model.RecipeIngredient;
import ryback.api.model.RecipeIngredientId;

public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, RecipeIngredientId> {
}
