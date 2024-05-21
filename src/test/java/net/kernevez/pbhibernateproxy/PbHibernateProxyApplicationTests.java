package net.kernevez.pbhibernateproxy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.AmountRepository;
import net.kernevez.pbhibernateproxy.entities.CurrencyEntity;
import net.kernevez.pbhibernateproxy.entities.CurrencyRepository;
import net.kernevez.pbhibernateproxy.entities.FxRateRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PbHibernateProxyApplication.class)
@Transactional
class PbHibernateProxyApplicationTests {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    FxRateRepository fxRateRepository;
    @Autowired
    AmountRepository amountRepository;

    private static final long EUR_ID = 559047381272772863L;
    private static final long USD_ID = 559047415092265192L;

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

        @Test
        void showIssueWithProxy() {
            // Given
            var eur = currencyRepository.getReferenceById(EUR_ID);
            var usd = currencyRepository.getReferenceById(USD_ID);

            // When
            var result = fxRateRepository.findByBaseAndQuote(usd, eur);

            // Then
            assertEquals("USD", usd.getIsoCode()); // <- Proxy is able to access to fields
            assertEquals("EUR", eur.getIsoCode()); // <- Proxy is able to access to fields
            assertTrue(result.isPresent()); // <- fails because the query do not get the isocode
            // select fre1_0.id,fre1_0.base_currency_iso_code,fre1_0.quote_currency_iso_code,fre1_0.rate from fx_rate fre1_0 where fre1_0.base_currency_iso_code=? and fre1_0.quote_currency_iso_code=?
            // binding parameter (1:VARCHAR) <- [null]
            // binding parameter (2:VARCHAR) <- [null]
            var rate = result.get();
            assertEquals(0, new BigDecimal("0.9").compareTo(rate.getRate()));
            assertEquals("USD", rate.getBase().getIsoCode());
            assertEquals("EUR", rate.getQuote().getIsoCode());
        }

        @Test
        void showThereIsNotIssueIfCurrencyIsAlsoLoadedProxy() {
            // Given
            var eur_proxy = currencyRepository.getReferenceById(EUR_ID);
            var eur = Hibernate.unproxy(eur_proxy, CurrencyEntity.class); // <- Un-proxy EUR solve the issue for EUR
            amountRepository.findAll().getFirst().getCurrency(); // <- Preload an amount is enough for USD
            var usd = currencyRepository.getReferenceById(USD_ID);

            // When
            var result = fxRateRepository.findByBaseAndQuote(usd, eur);

            // Then
            assertEquals("USD", usd.getIsoCode());
            assertEquals("EUR", eur.getIsoCode());
            assertTrue(result.isPresent()); // <- Now succeed
            // select fre1_0.id,fre1_0.base_currency_iso_code,fre1_0.quote_currency_iso_code,fre1_0.rate from fx_rate fre1_0 where fre1_0.base_currency_iso_code=? and fre1_0.quote_currency_iso_code=?
            // binding parameter (1:VARCHAR) <- [USD]
            // binding parameter (2:VARCHAR) <- [EUR]
            var rate = result.get();
            assertEquals(0, new BigDecimal("0.9").compareTo(rate.getRate()));
            assertEquals("USD", rate.getBase().getIsoCode());
            assertEquals("EUR", rate.getQuote().getIsoCode());
        }

    }


}
