package com.cactus.user.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CactusUserApi3
 * Class: MessageDTO
 * Created by hapo on 2019-12-03.
 * Description:
 */

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    private Long messageId;
    private String contents;
    private Long receivedUserId;
    private Long sendUserId;
    private LocalDateTime insertTime;
    private String isLike;

    @Builder
    public MessageDTO(Long messageId, String contents, Long receivedUserId, Long sendUserId, LocalDateTime insertTime, String isLike) {
        this.messageId = messageId;
        this.contents = contents;
        this.receivedUserId = receivedUserId;
        this.sendUserId = sendUserId;
        this.insertTime = insertTime;
        this.isLike = isLike;
    }
}
