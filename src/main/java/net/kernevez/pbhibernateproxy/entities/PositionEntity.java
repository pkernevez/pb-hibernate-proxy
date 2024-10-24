package net.kernevez.pbhibernateproxy.entities;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import net.kernevez.pbhibernateproxy.sql.TsidType;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "POSITION")
public class PositionEntity {
    @Id
    @Type(TsidType.class)
    private TSID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private AccountEntity account;
    private LocalDate businessDate;

    @Embedded
    @Column(nullable = true)
    @AttributeOverrides({
//            @AttributeOverride(name = "quantity", column = @Column(name = "NAV_QUANTITY")),
            @AttributeOverride(name = "ccy", column = @Column(nullable = true))
    })
    private EmbeddableAmount nav;

    @ElementCollection
    @CollectionTable(
            name = "POSITION_HOLDING",
            joinColumns = @JoinColumn(name = "POSITION_ID", referencedColumnName = "ID", nullable = false))
    private List<PositionHolding> holdings = new ArrayList<>();

    public PositionEntity() {
    }

    public PositionEntity(TSID id, AccountEntity account, LocalDate businessDate, EmbeddableAmount nav, List<PositionHolding> holdings) {
        this.id = id;
        this.account = account;
        this.businessDate = businessDate;
        this.nav = nav;
        this.holdings = holdings;
    }

    public TSID getId() {
        return id;
    }

    public PositionEntity setId(TSID id) {
        this.id = id;
        return this;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public PositionEntity setAccount(AccountEntity account) {
        this.account = account;
        return this;
    }

    public LocalDate getBusinessDate() {
        return businessDate;
    }

    public PositionEntity setBusinessDate(LocalDate businessDate) {
        this.businessDate = businessDate;
        return this;
    }

    public EmbeddableAmount getNav() {
        return nav;
    }

    public PositionEntity setNav(EmbeddableAmount nav) {
        this.nav = nav;
        return this;
    }

    public List<PositionHolding> getHoldings() {
        return holdings;
    }

}
