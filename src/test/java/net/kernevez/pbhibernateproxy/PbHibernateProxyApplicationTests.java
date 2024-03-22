package net.kernevez.pbhibernateproxy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.InstrumentRepository;
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
    InstrumentRepository instrumentRepository;
//    @Autowired
//    AmountRepository sut;

    @Sql(statements = {
            "INSERT INTO INSTRUMENT (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047328680968448, 'Currency CHF', 'CHF', null)",
            "INSERT INTO INSTRUMENT (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047381272772863, 'Currency EUR', 'EUR', null)",
            "INSERT INTO INSTRUMENT (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047415092265192, 'Currency USD', 'USD', null)",
            "INSERT INTO INSTRUMENT (id, NAME, ISO_CODE, BASE_CURRENCY_ISO_CODE) values ( 559047429040537816, 'IBM', null, 'USD')",
    })
    @Nested
    class WithData {

        @Test
        void checkLoading() {
            assertEquals(4, instrumentRepository.count());
        }
    }


}
