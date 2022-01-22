package com.cactus.api.message.CactusMessageApi.service;

import com.cactus.api.message.CactusMessageApi.domain.Push;
import com.cactus.api.message.CactusMessageApi.domain.SendType;

import java.util.List;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: PushService
 * Created by hapo on 2019-11-15.
 * Description:
 */
public interface PushService {
    List<Push> findBySendTypeAndSendIdOrderByInsertTimeDesc(SendType sendType, Long sendId);
    List<Push> findByUserIdOrderByInsertTimeDesc(Long userId);

    void sendPushMessage(Push push);
    Optional<Push> save(Push push);

}
