package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.Objects;

@Embeddable
public class PositionHolding {
    @Embedded
    private EmbeddableAmount valueInAccountCurrency;
    @Embedded
    private EmbeddableAmount valueInOtherCurrency;

    public PositionHolding() {
    }

    public PositionHolding(EmbeddableAmount valueInAccountCurrency,
                           EmbeddableAmount valueInOtherCurrency) {
        this.valueInAccountCurrency = valueInAccountCurrency;
        this.valueInOtherCurrency = valueInOtherCurrency;
    }

    public EmbeddableAmount getValueInAccountCurrency() {
        return valueInAccountCurrency;
    }

    public PositionHolding setValueInAccountCurrency(EmbeddableAmount valueInAccountCurrency) {
        this.valueInAccountCurrency = valueInAccountCurrency;
        return this;
    }

    public EmbeddableAmount getValueInOtherCurrency() {
        return valueInOtherCurrency;
    }

    public PositionHolding setValueInOtherCurrency(EmbeddableAmount valueInOtherCurrency) {
        this.valueInOtherCurrency = valueInOtherCurrency;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionHolding that)) return false;
        return Objects.equals(getValueInAccountCurrency(), that.getValueInAccountCurrency())
               && Objects.equals(getValueInOtherCurrency(), that.getValueInOtherCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValueInAccountCurrency(), getValueInOtherCurrency());
    }

}
