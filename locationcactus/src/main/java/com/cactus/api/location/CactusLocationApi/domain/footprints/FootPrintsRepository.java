package com.cactus.api.location.CactusLocationApi.domain.footprints;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FootPrintsRepository extends JpaRepository<FootPrints, Long> {

    Optional<FootPrints> findById(Long id);

    List<FootPrints> findByUserIdOrderBySeq(Long id);

    List<FootPrints> findByUserIdAndInsertTimeBetweenOrderBySeqDesc(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    Optional<FootPrints> findFirstByUserIdAndInsertTimeBetweenOrderBySeqDesc(Long userId, LocalDateTime startTime, LocalDateTime endTime);

}
