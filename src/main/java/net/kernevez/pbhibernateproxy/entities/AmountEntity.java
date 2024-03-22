package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "AMOUNT")
@SuppressWarnings("JpaDataSourceORMInspection")
public class AmountEntity {
    @Id
    private Long id;

    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ISO_CODE", referencedColumnName = "isoCode")
    private CurrencyEntity currency;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public AmountEntity setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public AmountEntity setCurrency(CurrencyEntity currency) {
        this.currency = currency;
        return this;
    }
}
