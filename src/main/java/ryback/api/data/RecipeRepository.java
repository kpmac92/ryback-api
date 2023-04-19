package ryback.api.data;

import org.springframework.data.repository.CrudRepository;
import ryback.api.models.Recipe;

import java.util.UUID;

public interface RecipeRepository extends CrudRepository<Recipe, UUID> {

}
