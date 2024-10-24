package net.kernevez.pbhibernateproxy.entities;

import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, TSID> {
}
