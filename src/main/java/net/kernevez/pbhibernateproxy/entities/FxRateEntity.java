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

}
