package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    private PositionHolding holding;

    public PositionEntity() {
    }

    public PositionEntity(Long id, AccountEntity account, LocalDate businessDate, PositionHolding holding) {
        this.id = id;
        this.account = account;
        this.businessDate = businessDate;
        this.holding = holding;
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

    public PositionHolding getHolding() {
        return holding;
    }

}
