package ryback.api.model;

import javax.persistence.*;

@Entity
@Table(name="recipeIngredients")
public class RecipeIngredient {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="numerator", column=@Column(name = "amountNumerator")),
            @AttributeOverride(name="denominator", column = @Column(name="amountDenominator"))
    })
    private IngredientAmount amount;
    private Boolean isPrimary;
    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name="recipeId")
    Recipe recipe;
    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name="ingredientId")
    Ingredient ingredient;

    @EmbeddedId
    RecipeIngredientId recipeIngredientId;

    public RecipeIngredient() {
        this.recipeIngredientId = new RecipeIngredientId();
    }

    public RecipeIngredient(Boolean isPrimary, Recipe recipe, Ingredient ingredient,
                            Integer amountNumerator, Integer amountDenominator) {
        this.isPrimary = isPrimary;
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = new IngredientAmount(amountNumerator, amountDenominator);
        this.recipeIngredientId = new RecipeIngredientId();
    }

    public IngredientAmount getAmount() {
        return amount;
    }

    public void setAmount(IngredientAmount amount) {
        this.amount = amount;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public RecipeIngredientId getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public void setRecipeIngredientId(RecipeIngredientId recipeIngredientId) {
        this.recipeIngredientId = recipeIngredientId;
    }
}
