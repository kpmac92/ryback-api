package ryback.api;

import org.junit.Assert;
import org.junit.Test;
import ryback.api.controller.IngredientController;

public class IngredientControllerTest {

    @Test
    public void getReturnsHelloWorld() {
        IngredientController subject = new IngredientController();

        String result = subject.get();

        Assert.assertEquals("here's an ingredient, from inside docker!", result);
    }
}
