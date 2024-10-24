package net.kernevez.pbhibernateproxy;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "OptionalGetWithoutIsPresent"})
@SpringBootTest
@Transactional
class PbWithRelationTest {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PositionRepository sut;

    @Autowired
    private EntityManager entityManager;

    private final long ACC_ID = 637226063293710801L;
    private final long USD_ID = 637226003797508422L;

    @Test
    @Sql(statements = {
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 637225948625633732, 'Currency EUR', 'EUR', null)",
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( " + USD_ID + ", 'Currency USD', 'USD', null)",
            "INSERT INTO ACCOUNT  (id, NAME, PRIMARY_CURRENCY_ISO_CODE) values ( " + ACC_ID + ", 'JOHN DOE', 'EUR')",
            "INSERT INTO ACCOUNT_SECONDARY_CURRENCIES  (ACCOUNT_ID, CURRENCY_ISO_CODE) values ( " + ACC_ID + ", 'USD')",
//            "INSERT INTO POSITION (id, ACCOUNT_ID, BUSINESS_DATE, NAV_QUANTITY, NAV_CCY_ISO_CODE) values ( 20, 10, '2024-01-23', 8000, 'EUR')",
//            "INSERT INTO POSITION_HOLDING (POSITION_ID, HOLDINGS_QUANTITY, HOLDINGS_INSTRUMENT_ID, HOLDINGS_VALUE_IN_ACCOUNT_CURRENCY_QUANTITY,HOLDINGS_VALUE_IN_ACCOUNT_CURRENCY_CCY_ISO_CODE, HOLDINGS_VALUE_IN_OTHER_CURRENCY_QUANTITY, HOLDINGS_VALUE_IN_OTHER_CURRENCY_CCY_ISO_CODE) values ( 20, 9175, 2, null, null, 9175, 'USD')",
    })
    void createAnErrorWithProxy() {
        // Given
        LocalDate today = LocalDate.of(2024, 1, 23);
        AccountEntity account = accountRepository.findById(TSID.from(ACC_ID)).get();
        CurrencyEntity usd = currencyRepository.findById(TSID.from(USD_ID)).get();
//        var lastPosition = sut.findFirstByAccountAndBusinessDateLessThanEqualOrderByBusinessDateDesc(account, today).get();
//        var inflation = 1.01;
//        var newHoldings = lastPosition.getHoldings().stream()
//                                      .map(h -> new PositionHolding(h.getQuantity(), h.getInstrument(),
//                                                                    h.getValueInAccountCurrency().multiply(inflation),
//                                                                    h.getValueInOtherCurrency().multiply(inflation)))
//                                      .toList();
        TSID posId = TSID.from("0HNZ2BFQBW0CE");
        var newPosition = new PositionEntity(posId, account, today, null, List.of());
        sut.save(newPosition);
        entityManager.flush();
        entityManager.clear();

        newPosition = positionRepository.getReferenceById(posId);
        newPosition.setNav(new EmbeddableAmount(BigDecimal.ZERO, usd));

        // When
        entityManager.flush();

        // Then...


    }


}
