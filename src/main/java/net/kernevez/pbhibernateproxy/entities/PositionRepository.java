package net.kernevez.pbhibernateproxy.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    Optional<PositionEntity> findFirstByAccountAndBusinessDateLessThanEqualOrderByBusinessDateDesc(AccountEntity account,
                                                                                                   LocalDate businessDate);
}
