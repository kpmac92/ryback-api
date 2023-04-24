package ryback.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ryback.api.rest.RecipeRequestObject;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private Integer time;

    @OneToMany(mappedBy = "recipe",
            targetEntity = RecipeIngredient.class)
    @JsonIgnore
    private List<RecipeIngredient> recipeIngredients;

    public Recipe() {
    }

    public Recipe(RecipeRequestObject recipeRequestObject) {
        this.id = recipeRequestObject.getId();
        this.name = recipeRequestObject.getName();
        this.description = recipeRequestObject.getDescription();
        this.time = recipeRequestObject.getTime();
    }

    public Recipe(UUID id, String name, String description, Integer time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
