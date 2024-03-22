package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class AmountEntity {

    private BigDecimal quantity;

    @ManyToOne
    private InstrumentEntity currency;
}
