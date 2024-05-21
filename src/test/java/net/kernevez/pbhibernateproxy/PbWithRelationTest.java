package net.kernevez.pbhibernateproxy;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.*;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "OptionalGetWithoutIsPresent"})
@SpringBootTest
@Transactional
class PbWithRelationTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PositionRepository sut;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Sql(statements = {
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 1, 'Currency EUR', 'EUR', null)",
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 2, 'Currency USD', 'USD', null)",
            "INSERT INTO ACCOUNT  (id, NAME, PRIMARY_CURRENCY_ISO_CODE) values ( 10, 'JOHN DOE', 'EUR')",
            "INSERT INTO ACCOUNT_SECONDARY_CURRENCIES  (ACCOUNT_ID, CURRENCY_ISO_CODE) values ( 10, 'USD')",
            "INSERT INTO POSITION (id, ACCOUNT_ID, BUSINESS_DATE, NAV_QUANTITY, NAV_CCY_ISO_CODE) values ( 20, 10, '2024-01-23', 8000, 'EUR')",
            "INSERT INTO POSITION_HOLDING (POSITION_ID, HOLDINGS_QUANTITY, HOLDINGS_INSTRUMENT_ID, HOLDINGS_VALUE_IN_ACCOUNT_CURRENCY_QUANTITY,HOLDINGS_VALUE_IN_ACCOUNT_CURRENCY_CCY_ISO_CODE, HOLDINGS_VALUE_IN_OTHER_CURRENCY_QUANTITY, HOLDINGS_VALUE_IN_OTHER_CURRENCY_CCY_ISO_CODE) values ( 20, 9175, 2, 8000, 'USD', 9175, 'USD')",
    })
    void createAnErrorWithProxy() {
        // Given
        LocalDate today = LocalDate.of(2024, 1, 23);
        AccountEntity account = accountRepository.findById(10L).get();
        var lastPosition = sut.findFirstByAccountAndBusinessDateLessThanEqualOrderByBusinessDateDesc(account, today).get();
        var inflation = 1.01;
        var newHoldings = lastPosition.getHoldings().stream()
                                      .map(h -> new PositionHolding(h.getQuantity(), h.getInstrument(),
                                                                    h.getValueInAccountCurrency().multiply(inflation),
                                                                    h.getValueInOtherCurrency().multiply(inflation)))
                                      .toList();
        var newPosition = new PositionEntity(21L, account, today, lastPosition.getNav().multiply(inflation), newHoldings);
        sut.save(newPosition);

        // Then...
        assertEquals(1, newHoldings.size());
        var holding = newHoldings.getFirst();
        assertEquals("USD", holding.getValueInOtherCurrency().getCcy().getIsoCode()); // <- valueInOther.currency is USD
        var e = assertThrows(ConstraintViolationException.class,
                             () -> entityManager.flush()); // But unable to save, a null value is passed as currency
        assertEquals("holdings_value_in_other_currency_ccy_iso_code\" of relation \"position_holding",
                     e.getConstraintName()); // <- valueInAccountCurrency is null in DB...
        assertTrue(e.getMessage().contains(
                "holdings_value_in_other_currency_ccy_iso_code\" of relation \"position_holding\" violates not-null constraint"));
/*
The request is
insert into position_holding (position_id,"holdings_instrument_id","holdings_quantity","holdings_value_in_account_currency_ccy_iso_code","holdings_value_in_account_currency_quantity","holdings_value_in_other_currency_ccy_iso_code","holdings_value_in_other_currency_quantity") values (?,?,?,?,?,?,?)
binding parameter (1:BIGINT) <- [21]
binding parameter (2:BIGINT) <- [2]
binding parameter (3:NUMERIC) <- [9175]
binding parameter (4:VARCHAR) <- [null]
binding parameter (5:NUMERIC) <- [8080.00]
binding parameter (6:VARCHAR) <- [null]
binding parameter (7:NUMERIC) <- [9266.75]
 */


    }


}
