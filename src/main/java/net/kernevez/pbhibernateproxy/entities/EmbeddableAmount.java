package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Objects;

import static java.lang.String.format;

@Embeddable
public class EmbeddableAmount {
    private BigDecimal quantity;
    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "isoCode", nullable = false)
    private CurrencyEntity1 ccy;

    public EmbeddableAmount() {
    }

    public EmbeddableAmount(BigDecimal quantity, CurrencyEntity1 ccy) {
        if (ccy == null) {
            throw new RuntimeException(format("Currency can't be null for an amount with a quantity of %s", quantity));
        }
        if (quantity == null) {
            throw new RuntimeException(format("Quantity can't be null for an amount with a currency %s", ccy));
        }
        this.quantity = quantity;
        this.ccy = ccy;
    }

    public EmbeddableAmount multiply(double multiplicand) {
        return new EmbeddableAmount(quantity.multiply(BigDecimal.valueOf(multiplicand)), ccy);
    }


    public BigDecimal getQuantity() {
        return quantity;
    }

    public EmbeddableAmount setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public CurrencyEntity1 getCcy() {
        return ccy;
    }

    public EmbeddableAmount setCcy(CurrencyEntity1 ccy) {
        this.ccy = ccy;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmbeddableAmount amount)) return false;
        return bigDecimalEquals(getQuantity(), amount.getQuantity())
               && Objects.equals(getCcy(), amount.getCcy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity().stripTrailingZeros(), getCcy());
    }

    public static boolean bigDecimalEquals(BigDecimal first, BigDecimal second) {
        boolean bothAreNull = first == null && second == null;
        return bothAreNull || (first != null && second != null && first.compareTo(second) == 0);
    }

}
