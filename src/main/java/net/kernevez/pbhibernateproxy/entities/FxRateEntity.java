package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "FX_RATE")
@SuppressWarnings("JpaDataSourceORMInspection")
public class FxRateEntity {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_CURRENCY_ISO_CODE", referencedColumnName = "isoCode")
    private CurrencyEntity base;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUOTE_CURRENCY_ISO_CODE", referencedColumnName = "isoCode")
    private CurrencyEntity quote;

    private BigDecimal rate;

    public CurrencyEntity getBase() {
        return base;
    }

    public FxRateEntity setBase(CurrencyEntity base) {
        this.base = base;
        return this;
    }

    public CurrencyEntity getQuote() {
        return quote;
    }

    public FxRateEntity setQuote(CurrencyEntity quote) {
        this.quote = quote;
        return this;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public FxRateEntity setRate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }
}
