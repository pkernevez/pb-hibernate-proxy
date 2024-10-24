package net.kernevez.pbhibernateproxy.entities;

import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, TSID> {
}
