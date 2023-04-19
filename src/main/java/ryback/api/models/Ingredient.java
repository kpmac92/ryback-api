package ryback.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Boolean discrete;

    public Ingredient() {
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

    public Boolean getDiscrete() {
        return discrete;
    }

    public void setDiscrete(Boolean discrete) {
        this.discrete = discrete;
    }
}
