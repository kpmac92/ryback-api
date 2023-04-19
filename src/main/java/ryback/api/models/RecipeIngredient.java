package ryback.api.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="recipeIngredients")
@IdClass(RecipeIngredientId.class)
public class RecipeIngredient {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="numerator", column=@Column(name = "amountNumerator")),
            @AttributeOverride(name="denominator", column = @Column(name="amountDenominator"))
    })
    private IngredientAmount amount;
    private Boolean primary;
    @ManyToOne
    @JoinColumn(name="recipeId")
    private Recipe recipe;
    @ManyToOne
    @JoinColumn(name="ingredientId")
    private Ingredient ingredient;

    @Id
    @Column(name="recipeId", insertable = false, updatable = false)
    private UUID recipeId;
    @Id
    @Column(name="ingredientId", insertable = false, updatable = false)
    private UUID ingredientId;

    public RecipeIngredient() {
    }

    public IngredientAmount getAmount() {
        return amount;
    }

    public void setAmount(IngredientAmount amount) {
        this.amount = amount;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
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
}
