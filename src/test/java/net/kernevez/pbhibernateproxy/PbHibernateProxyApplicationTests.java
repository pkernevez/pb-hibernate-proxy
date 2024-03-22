package net.kernevez.pbhibernateproxy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.CurrencyRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
@SpringBootTest(classes = PbHibernateProxyApplication.class)
@Transactional
class PbHibernateProxyApplicationTests {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    CurrencyRepository currencyRepository;
//    @Autowired
//    AmountRepository sut;

    @Sql(statements = {
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047328680968448, 'Currency CHF', 'CHF', null)",
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047381272772863, 'Currency EUR', 'EUR', null)",
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047415092265192, 'Currency USD', 'USD', null)",
            "INSERT INTO CURRENCY (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047429040537816, 'IBM', null, 'USD')",
            "INSERT INTO AMOUNT   (id, QUANTITY, CURRENCY_ISO_CODE) values ( 559051800295702744, 1000, 'USD')",
            "INSERT INTO FX_RATE  (id, BASE_CURRENCY_ISO_CODE, QUOTE_CURRENCY_ISO_CODE, RATE) values ( 559051800304091225, 'USD', 'EUR', 0.9)",
            "INSERT INTO FX_RATE  (id, BASE_CURRENCY_ISO_CODE, QUOTE_CURRENCY_ISO_CODE, RATE) values ( 559051800304091226, 'CHF', 'EUR', 1.1)",
            "INSERT INTO FX_RATE  (id, BASE_CURRENCY_ISO_CODE, QUOTE_CURRENCY_ISO_CODE, RATE) values ( 559051800304091227, 'EUR', 'EUR', 1)",
    })
    @Nested
    class WithData {

        @Test
        void checkLoading() {
            assertEquals(4, currencyRepository.count());
        }
    }


}
