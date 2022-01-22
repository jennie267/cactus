package com.cactus.api.message.CactusMessageApi.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: MessageRepository
 * Created by hapo on 2019-11-15.
 * Description:
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceivedUserIdOrderByInsertTimeDesc(Long receivedUserId);
    List<Message> findByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(Long receivedUserId, Long sendUserId);
    Optional<Message> findFirstByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(Long receivedUserId, Long sendUserId);
    Optional<Message> findByMessageId(Long messageId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value= "update message set is_like = :isLike where message_id = :messageId")
    int updateByMessageId(@Param("messageId") Long messageId, @Param("isLike") String isLike);

    @Query(nativeQuery = true, value= "select * from message where date_format(insert_time, '%Y-%m-%d') = current_date() and received_user_id = :receivedUserId order by insert_time desc ")
    List<Message> findByUserIdAndToday(Long receivedUserId);
}
