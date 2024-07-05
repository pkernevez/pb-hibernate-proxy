package net.kernevez.pbhibernateproxy;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.kernevez.pbhibernateproxy.entities.AccountRepository;
import net.kernevez.pbhibernateproxy.entities.PositionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection",})
@SpringBootTest
@Transactional
class PbLoadingTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PositionRepository sut;

    @Autowired
    private EntityManager entityManager;

    @Test
    void errorDuringLoading() {
        // Given
        var accounts = accountRepository.findAll();

        // Then...
        assertNotNull(accounts);
    }


}
