package com.cactus.api.message.CactusMessageApi.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CactusMessageApi
 * Class: MessageDTO
 * Created by hapo on 2019-11-28.
 * Description:
 */

@Getter
@Setter
public class MessageDTO {
    private Long messageId;
    private String contents;
    private Long receivedUserId;
    private String receivedUserName;
    private String receivedUserProfileUrl;
    private Long sendUserId;
    private String sendUserName;
    private String sendUserProfileUrl;
    private LocalDateTime insertTime;
    private String isLike;

    public MessageDTO() {
    }

    @Builder
    public MessageDTO(Long messageId, String contents, Long receivedUserId, String receivedUserName, String receivedUserProfileUrl, Long sendUserId, String sendUserName, String sendUserProfileUrl, LocalDateTime insertTime, String isLike) {
        this.messageId = messageId;
        this.contents = contents;
        this.receivedUserId = receivedUserId;
        this.receivedUserName = receivedUserName;
        this.receivedUserProfileUrl = receivedUserProfileUrl;
        this.sendUserId = sendUserId;
        this.sendUserName = sendUserName;
        this.sendUserProfileUrl = sendUserProfileUrl;
        this.insertTime = insertTime;
        this.isLike = isLike;
    }
}
