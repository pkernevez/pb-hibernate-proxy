package net.kernevez.pbhibernateproxy.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FxRateRepository extends JpaRepository<FxRateEntity, Long> {

    Optional<FxRateEntity> findByBaseAndQuote(CurrencyEntity base, CurrencyEntity quote);

}
