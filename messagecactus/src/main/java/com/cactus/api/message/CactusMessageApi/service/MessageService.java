package com.cactus.api.message.CactusMessageApi.service;

import com.cactus.api.message.CactusMessageApi.domain.Message;
import com.cactus.api.message.CactusMessageApi.domain.dto.MessageDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: MessageService
 * Created by hapo on 2019-11-15.
 * Description:
 */
public interface MessageService {
    List<MessageDTO> findByReceivedUserIdOrderByInsertTimeDesc(Long receivedUserId, String authHeader);
    List<MessageDTO> findByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(Long receivedUserId, Long sendUserId, String authHeader);
    Optional<Message> findFirstByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(Long receivedUserId, Long sendUserId);

    Optional<Message> sendMessage(Message message);

    Optional<Message> findByMessageId(Long messageId);

    int updateByMessageId(Long messageId, String isLike);

    List<MessageDTO> findByUserIdAndToday(Long receivedUserId, String authHeader);
}
