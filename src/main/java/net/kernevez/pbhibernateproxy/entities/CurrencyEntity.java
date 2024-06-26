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
public class CurrencyEntity {
    @Id
    private Long id;
    @NaturalId
    /* Uppercase iso code of the currency, following ISO 4217  */
    private String isoCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_CURRENCY_ISO_CODE", referencedColumnName = "isoCode")
    private CurrencyEntity baseCurrency;

    public String getIsoCode() {
        return isoCode;
    }

    public CurrencyEntity setIsoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    public CurrencyEntity getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyEntity setBaseCurrency(CurrencyEntity baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }
}
