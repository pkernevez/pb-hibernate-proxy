package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "POSITION")
public class PositionEntity {
    @Id
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private AccountEntity account;
    private LocalDate businessDate;
    @Embedded
    private EmbeddableAmount nav;
    @ElementCollection
    @CollectionTable(
            name = "POSITION_HOLDING",
            joinColumns = @JoinColumn(name = "POSITION_ID", referencedColumnName = "ID", nullable = false))
    private List<PositionHolding> holdings = new ArrayList<>();

    public PositionEntity() {
    }

    public PositionEntity(Long id, AccountEntity account, LocalDate businessDate, EmbeddableAmount nav, List<PositionHolding> holdings) {
        this.id = id;
        this.account = account;
        this.businessDate = businessDate;
        this.nav = nav;
        this.holdings = holdings;
    }

    public Long getId() {
        return id;
    }

    public PositionEntity setId(Long id) {
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
