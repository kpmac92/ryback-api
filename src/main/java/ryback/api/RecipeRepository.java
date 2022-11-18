package ryback.api;

import org.springframework.data.repository.CrudRepository;
import ryback.api.Recipe;

import java.util.UUID;

public interface RecipeRepository extends CrudRepository<Recipe, UUID> {

}
