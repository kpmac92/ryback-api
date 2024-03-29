package ryback.api.service;

import org.hibernate.service.spi.InjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import ryback.api.data.IngredientRepository;
import ryback.api.data.RecipeIngredientRepository;
import ryback.api.data.RecipeRepository;
import ryback.api.model.Ingredient;
import ryback.api.model.IngredientAmount;
import ryback.api.model.Recipe;
import ryback.api.model.RecipeIngredient;
import ryback.api.rest.RecipeIngredientRequestObject;
import ryback.api.rest.RecipeRequestObject;

import java.util.*;

import static org.junit.Assert.assertEquals;

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

    @Captor
    private ArgumentCaptor<Ingredient> ingredientCaptor;

    @Captor
    private ArgumentCaptor<Recipe> recipeCaptor;

    @Captor
    private ArgumentCaptor<RecipeIngredient> recipeIngredientCaptor;

    @Test
    public void saveRecipeCreatesNewIngredientWhenNoneExist() {
        RecipeRequestObject input = buildRecipeRequestObject();
        Recipe returnedRecipe = new Recipe();
        returnedRecipe.setId(UUID.randomUUID());
        Ingredient returnedIngredient = new Ingredient();
        returnedIngredient.setId(UUID.randomUUID());

        Mockito.when(mockRecipeRepository.save(ArgumentMatchers.any()))
                        .thenReturn(returnedRecipe);
        Mockito.when(mockIngredientRepository.save(ArgumentMatchers.any()))
                        .thenReturn(returnedIngredient);

        subject.saveRecipe(input);

        Mockito.verify(mockIngredientRepository).save(ingredientCaptor.capture());
        Mockito.verify(mockRecipeRepository).save(recipeCaptor.capture());
        Mockito.verify(mockRecipeIngredientRepository).save(recipeIngredientCaptor.capture());

        Ingredient savedIngredient = ingredientCaptor.getValue();
        Recipe savedRecipe = recipeCaptor.getValue();
        RecipeIngredient savedRecipeIngredient = recipeIngredientCaptor.getValue();

        assertEquals("milk", savedIngredient.getName());
        assertEquals(false, savedIngredient.getIsDiscrete());

        assertEquals("pancakes", savedRecipe.getName());
        assertEquals("A Description", savedRecipe.getDescription());
        assertEquals(Integer.valueOf(30), savedRecipe.getTime());

        assertEquals(returnedIngredient.getId(), savedRecipeIngredient.getIngredient().getId());
        assertEquals(returnedRecipe.getId(), savedRecipeIngredient.getRecipe().getId());
        assertEquals(Integer.valueOf(2), savedRecipeIngredient.getAmount().getDenominator());
        assertEquals(Integer.valueOf(1), savedRecipeIngredient.getAmount().getNumerator());
        assertEquals(true, savedRecipeIngredient.getIsPrimary());
    }

    @Test
    public void saveRecipeUsesExistingIngredientWhenExists() {
        RecipeRequestObject input = buildRecipeRequestObject();
        Recipe returnedRecipe = new Recipe();
        returnedRecipe.setId(UUID.randomUUID());
        Ingredient returnedIngredient = new Ingredient();
        returnedIngredient.setId(UUID.randomUUID());

        Mockito.when(mockRecipeRepository.save(ArgumentMatchers.any()))
                .thenReturn(returnedRecipe);
        Mockito.when(mockIngredientRepository.findByName("milk"))
                .thenReturn(Optional.of(returnedIngredient));

        subject.saveRecipe(input);

        Mockito.verify(mockIngredientRepository, Mockito.never()).save(ArgumentMatchers.any());
        Mockito.verify(mockRecipeRepository).save(recipeCaptor.capture());
        Mockito.verify(mockRecipeIngredientRepository).save(recipeIngredientCaptor.capture());

        RecipeIngredient savedRecipeIngredient = recipeIngredientCaptor.getValue();

        assertEquals(returnedRecipe.getId(), savedRecipeIngredient.getRecipe().getId());
        assertEquals(returnedIngredient.getId(), savedRecipeIngredient.getIngredient().getId());
    }

    @Test
    public void saveRecipeUpdatesExistingRecipeIngredient(){
        UUID recipeId = UUID.randomUUID();
        UUID ingredientId = UUID.randomUUID();
        RecipeRequestObject input = buildRecipeRequestObject();
        input.setId(recipeId);
        input.getRecipeIngredients().get(0).setIngredientId(ingredientId);
        input.getRecipeIngredients().get(0).setAmountNumerator(3);

        Ingredient existingIngredient = new Ingredient();
        existingIngredient.setId(ingredientId);
        existingIngredient.setName("milk");

        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(recipeId);

        RecipeIngredient existingRecipeIngredient = new RecipeIngredient();
        existingRecipeIngredient.setIngredient(existingIngredient);
        existingRecipeIngredient.setRecipe(existingRecipe);
        existingRecipeIngredient.setAmount(new IngredientAmount());

        Mockito.when(mockRecipeRepository.save(ArgumentMatchers.any())).thenReturn(existingRecipe);
        Mockito.when(mockRecipeIngredientRepository.findById(ArgumentMatchers.any()))
                        .thenReturn(Optional.of(existingRecipeIngredient));
        Mockito.when(mockIngredientRepository.findById(ArgumentMatchers.any()))
                        .thenReturn(Optional.of(existingIngredient));

        subject.saveRecipe(input);

        Mockito.verify(mockRecipeIngredientRepository).save(recipeIngredientCaptor.capture());

        RecipeIngredient savedRecipeIngredient = recipeIngredientCaptor.getValue();
        assertEquals(input.getId(), savedRecipeIngredient.getRecipe().getId());
        assertEquals(input.getRecipeIngredients().get(0).getIngredientId(), savedRecipeIngredient.getIngredient().getId());
        assertEquals(Integer.valueOf(2), savedRecipeIngredient.getAmount().getDenominator());
        assertEquals(Integer.valueOf(3), savedRecipeIngredient.getAmount().getNumerator());
        assertEquals(true, savedRecipeIngredient.getIsPrimary());
    }

    @Test
    public void saveRecipeCreatesNewRecipeIngredientWhenNameChanges() {
        UUID recipeId = UUID.randomUUID();
        UUID ingredientId = UUID.randomUUID();
        RecipeRequestObject input = buildRecipeRequestObject();
        input.setId(recipeId);
        input.getRecipeIngredients().get(0).setIngredientId(ingredientId);

        Ingredient existingIngredient = new Ingredient();
        existingIngredient.setId(ingredientId);
        existingIngredient.setName("eggs");

        Ingredient newIngredient = new Ingredient();
        newIngredient.setId(UUID.randomUUID());

        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(recipeId);

        RecipeIngredient existingRecipeIngredient = new RecipeIngredient();
        existingRecipeIngredient.setIngredient(existingIngredient);
        existingRecipeIngredient.setRecipe(existingRecipe);
        existingRecipeIngredient.setAmount(new IngredientAmount());

        Mockito.when(mockRecipeRepository.save(ArgumentMatchers.any())).thenReturn(existingRecipe);
        Mockito.when(mockRecipeIngredientRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(existingRecipeIngredient));
        Mockito.when(mockIngredientRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(existingIngredient));
        Mockito.when(mockIngredientRepository.save(ArgumentMatchers.any()))
                .thenReturn(newIngredient);

        subject.saveRecipe(input);

        Mockito.verify(mockRecipeIngredientRepository).delete(recipeIngredientCaptor.capture());
        RecipeIngredient deletedRecipeIngredient = recipeIngredientCaptor.getValue();
        Mockito.verify(mockRecipeIngredientRepository).save(recipeIngredientCaptor.capture());
        Mockito.verify(mockIngredientRepository).save(ingredientCaptor.capture());

        RecipeIngredient savedRecipeIngredient = recipeIngredientCaptor.getValue();
        Ingredient savedIngredient = ingredientCaptor.getValue();

        assertEquals(input.getRecipeIngredients().get(0).getIngredientId(), deletedRecipeIngredient.getIngredient().getId());

        assertEquals(input.getId(), savedRecipeIngredient.getRecipe().getId());
        assertEquals(newIngredient.getId(), savedRecipeIngredient.getIngredient().getId());
    }
    private static RecipeRequestObject buildRecipeRequestObject() {
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
        return input;
    }


}
