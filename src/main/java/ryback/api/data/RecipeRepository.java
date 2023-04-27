package ryback.api.data;

import org.springframework.data.repository.CrudRepository;
import ryback.api.model.Recipe;

import java.util.UUID;

public interface RecipeRepository extends CrudRepository<Recipe, UUID> {

}
