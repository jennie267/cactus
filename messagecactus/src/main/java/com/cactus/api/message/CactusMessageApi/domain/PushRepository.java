package com.cactus.api.message.CactusMessageApi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CactusMessageApi
 * Class: PushRepository
 * Created by hapo on 2019-11-15.
 * Description:
 */
public interface PushRepository extends JpaRepository<Push, Long>{
    List<Push> findBySendTypeAndSendIdOrderByInsertTimeDesc(SendType sendType, Long sendId);
    List<Push> findByUserIdOrderByInsertTimeDesc(Long userId);

}
