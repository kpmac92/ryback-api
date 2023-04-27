package ryback.api.model;

import org.apache.commons.numbers.fraction.Fraction;

import javax.persistence.Embeddable;

@Embeddable
public class IngredientAmount {
    private Integer numerator;
    private Integer denominator;

    public Fraction toFraction() {
        return Fraction.of(numerator, denominator);
    }
    public IngredientAmount() {
    }

    public IngredientAmount(Integer numerator, Integer denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Integer getNumerator() {
        return numerator;
    }

    public void setNumerator(Integer numerator) {
        this.numerator = numerator;
    }

    public Integer getDenominator() {
        return denominator;
    }

    public void setDenominator(Integer denominator) {
        this.denominator = denominator;
    }
}
