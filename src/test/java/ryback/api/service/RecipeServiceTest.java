package ryback.api.service;

import org.hibernate.service.spi.InjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import ryback.api.data.IngredientRepository;
import ryback.api.data.RecipeIngredientRepository;
import ryback.api.data.RecipeRepository;
import ryback.api.model.Ingredient;
import ryback.api.model.Recipe;
import ryback.api.rest.RecipeIngredientRequestObject;
import ryback.api.rest.RecipeRequestObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository mockRecipeRepository;

    @Mock
    private RecipeIngredientRepository mockRecipeIngredientRepository;

    @Mock
    private IngredientRepository mockIngredientRepository;

    @InjectMocks
    private RecipeService subject;

    @Test
    public void saveRecipeCreatesNewIngredientWhenNoneExist() {
        RecipeRequestObject input = new RecipeRequestObject();
        input.setName("pancakes");
        input.setDescription("A Description");
        input.setTime(30);

        RecipeIngredientRequestObject inputIngredient = new RecipeIngredientRequestObject();
        inputIngredient.setIngredientName("Milk");
        inputIngredient.setDiscrete(false);
        inputIngredient.setAmountDenominator(2);
        inputIngredient.setAmountNumerator(1);
        inputIngredient.setPrimary(true);

        input.setRecipeIngredients(List.of(inputIngredient));

        //todo: remove any matchers and assert db call params + return object
        Mockito.when(mockIngredientRepository.findByName("Milk")).thenReturn(Optional.empty());
        Mockito.when(mockRecipeRepository.save(ArgumentMatchers.any()))
                        .thenReturn(new Recipe());
        Mockito.when(mockIngredientRepository.save(ArgumentMatchers.any()))
                        .thenReturn(new Ingredient());

        subject.saveRecipe(input);

        Mockito.verify(mockIngredientRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void saveRecipeUsesExistingIngredientWhenExist() {

    }
}
