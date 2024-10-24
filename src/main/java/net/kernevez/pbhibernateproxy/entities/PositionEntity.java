package net.kernevez.pbhibernateproxy.entities;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import net.kernevez.pbhibernateproxy.sql.TsidType;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

@Entity
@Table(name = "POSITION")
public class PositionEntity {
    @Id
    @Type(TsidType.class)
    private TSID id;
    private LocalDate businessDate;

    @Embedded
    private EmbeddableAmount nav;

    public PositionEntity() {
    }

    public PositionEntity(TSID id, LocalDate businessDate, EmbeddableAmount nav) {
        this.id = id;
        this.businessDate = businessDate;
        this.nav = nav;
    }

    public TSID getId() {
        return id;
    }

    public PositionEntity setId(TSID id) {
        this.id = id;
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

}
