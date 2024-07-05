package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

@NaturalIdCache
@Entity
@Table(name = "CURRENCY")
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@SuppressWarnings("JpaDataSourceORMInspection")
public class CurrencyEntity1 {
    @Id
    private Long id;
    @NaturalId
    /* Uppercase iso code of the currency, following ISO 4217  */
    private String isoCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_CURRENCY_ISO_CODE", referencedColumnName = "isoCode")
    private CurrencyEntity1 baseCurrency;

    public String getIsoCode() {
        return isoCode;
    }

    public CurrencyEntity1 setIsoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    public CurrencyEntity1 getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyEntity1 setBaseCurrency(CurrencyEntity1 baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }
}
