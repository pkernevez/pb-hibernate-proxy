package net.kernevez.pbhibernateproxy.entities;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import net.kernevez.pbhibernateproxy.sql.TsidType;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {
    @Id
    @Type(TsidType.class)
    private TSID id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "isoCode", nullable = false, name = "PRIMARY_CURRENCY_ISO_CODE")
    private CurrencyEntity primaryCurrency;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "ACCOUNT_SECONDARY_CURRENCIES",
            joinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CURRENCY_ISO_CODE", referencedColumnName = "isoCode")
    )
    private Set<CurrencyEntity> secondaryCurrencies = new HashSet<>();

    public TSID getId() {
        return id;
    }

    public AccountEntity setId(TSID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
