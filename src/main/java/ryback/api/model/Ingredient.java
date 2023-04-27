package ryback.api.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Boolean isDiscrete;
    @OneToMany(mappedBy = "ingredient",
            targetEntity = RecipeIngredient.class)
    private List<RecipeIngredient> recipeIngredients;

    public Ingredient() {
    }

    public Ingredient(String name, Boolean isDiscrete) {
        this.name = name;
        this.isDiscrete = isDiscrete;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDiscrete() {
        return isDiscrete;
    }

    public void setIsDiscrete(Boolean isDiscrete) {
        this.isDiscrete = isDiscrete;
    }
}
