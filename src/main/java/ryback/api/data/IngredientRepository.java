package ryback.api.data;

import org.springframework.data.repository.CrudRepository;
import ryback.api.model.Ingredient;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends CrudRepository<Ingredient, UUID> {
    Optional<Ingredient> findByName(String name);
}
