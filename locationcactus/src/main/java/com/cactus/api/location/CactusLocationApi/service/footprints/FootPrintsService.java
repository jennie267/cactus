package com.cactus.api.location.CactusLocationApi.service.footprints;

import com.cactus.api.location.CactusLocationApi.domain.footprints.FootPrints;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FootPrintsService {

    Optional<FootPrints> findById(Long id);

    List<FootPrints> findByUserId(Long id);

    List<FootPrints> findByUserIdAndInsertTimeBetween(Long userId, String date);

    Optional<FootPrints> save(FootPrints footPrints);

    Optional<FootPrints> findFirstByUserIdAndInsertTimeBetweenOrderBySeqDesc(Long userId, String date);

    void delete(FootPrints footPrints);
    
}
