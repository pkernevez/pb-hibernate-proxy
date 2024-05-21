package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

import static net.kernevez.pbhibernateproxy.entities.EmbeddableAmount.bigDecimalEquals;

@Embeddable
public class PositionHolding {
    @Column(nullable = false)
    private BigDecimal quantity;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CurrencyEntity instrument;
    @Embedded
    private EmbeddableAmount valueInAccountCurrency;
    @Embedded
    private EmbeddableAmount valueInOtherCurrency;

    public PositionHolding() {
    }

    public PositionHolding(BigDecimal quantity, CurrencyEntity instrument, EmbeddableAmount valueInAccountCurrency,
                           EmbeddableAmount valueInOtherCurrency) {
        this.quantity = quantity;
        this.instrument = instrument;
        this.valueInAccountCurrency = valueInAccountCurrency;
        this.valueInOtherCurrency = valueInOtherCurrency;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public PositionHolding setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public CurrencyEntity getInstrument() {
        return instrument;
    }

    public PositionHolding setInstrument(CurrencyEntity instrument) {
        this.instrument = instrument;
        return this;
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
        return bigDecimalEquals(getQuantity(), that.getQuantity())
               && Objects.equals(getInstrument(), that.getInstrument())
               && Objects.equals(getValueInAccountCurrency(), that.getValueInAccountCurrency())
               && Objects.equals(getValueInOtherCurrency(), that.getValueInOtherCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity().stripTrailingZeros(), getInstrument(),
                            getValueInAccountCurrency(), getValueInOtherCurrency());
    }

}
