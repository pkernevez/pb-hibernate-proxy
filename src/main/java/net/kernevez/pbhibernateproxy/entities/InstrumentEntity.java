package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

@NaturalIdCache
@Entity
@Table( name ="INSTRUMENT")
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class InstrumentEntity {
    @Id
    private Long id;
    @NaturalId
    /* Uppercase iso code of the currency, following ISO 4217  */
    private String isoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_CURRENCY_ISO_CODE", referencedColumnName = "isoCode")
    @SuppressWarnings("JpaDataSourceORMInspection")
    private InstrumentEntity baseCurrency;

}
