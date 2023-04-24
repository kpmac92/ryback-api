package ryback.api.rest;

import ryback.api.models.RecipeIngredient;

import java.util.UUID;

public class RecipeIngredientRequestObject {
    private Integer amountNumerator;
    private Integer amountDenominator;
    //TODO: add units
    private Boolean primary;
    private UUID ingredientId;
    private String ingredientName;
    private Boolean discrete;

    public RecipeIngredientRequestObject() {
    }
    public RecipeIngredientRequestObject(RecipeIngredient recipeIngredient) {
        this.amountNumerator = recipeIngredient.getAmount().getNumerator();
        this.amountDenominator = recipeIngredient.getAmount().getDenominator();
        this.primary = recipeIngredient.getIsPrimary();
        this.ingredientId = recipeIngredient.getIngredient().getId();
        this.ingredientName = recipeIngredient.getIngredient().getName();
        this.discrete = recipeIngredient.getIngredient().getIsDiscrete();
    }

    public Integer getAmountNumerator() {
        return amountNumerator;
    }

    public void setAmountNumerator(Integer amountNumerator) {
        this.amountNumerator = amountNumerator;
    }

    public Integer getAmountDenominator() {
        return amountDenominator;
    }

    public void setAmountDenominator(Integer amountDenominator) {
        this.amountDenominator = amountDenominator;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(UUID ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Boolean getDiscrete() {
        return discrete;
    }

    public void setDiscrete(Boolean discrete) {
        this.discrete = discrete;
    }
}
