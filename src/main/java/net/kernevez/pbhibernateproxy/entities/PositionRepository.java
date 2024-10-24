package net.kernevez.pbhibernateproxy.entities;

import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<PositionEntity, TSID> {

    Optional<PositionEntity> findFirstByBusinessDateLessThanEqualOrderByBusinessDateDesc(
            LocalDate businessDate);
}
