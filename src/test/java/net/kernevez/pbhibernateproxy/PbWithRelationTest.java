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

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "OptionalGetWithoutIsPresent"})
@SpringBootTest
@Transactional
class PbWithRelationTest {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PositionRepository sut;

    @Autowired
    private EntityManager entityManager;

    private static final long USD_ID = 637226003797508422L;
    private static final long EUR_ID = 637225948625633732L;

    @Test
    @Sql(statements = {
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( " + EUR_ID + ", 'Currency EUR', 'EUR', null)",
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( " + USD_ID + ", 'Currency USD', 'USD', null)",
    })
    void createAnErrorWithProxy() {
        // Given
        LocalDate today = LocalDate.of(2024, 1, 23);
        CurrencyEntity usd = currencyRepository.findById(TSID.from(USD_ID)).get();
        CurrencyEntity chf = currencyRepository.findById(TSID.from(EUR_ID)).get();

        TSID posId = TSID.from("0HNZ2BFQBW0CE");
        var newPosition = new PositionEntity(posId, today, new EmbeddableAmount(BigDecimal.TEN, chf));
        sut.save(newPosition);
        entityManager.flush();
        entityManager.clear();

        newPosition = positionRepository.getReferenceById(posId);
        newPosition.setNav(new EmbeddableAmount(BigDecimal.ZERO, usd));

        // When => Boom
        entityManager.flush();

        // Then...


    }


}
