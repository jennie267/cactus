package com.cactus.user.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CactusUserApi3
 * Class: ChildBookDTO
 * Created by hapo on 2019-12-03.
 * Description:
 */

@Getter
@Setter
@NoArgsConstructor
public class ChildBookDTO {

    int userId;
    int childId;
    String childImageUrl;
    String childName;
    String childPhone;
    String lastMessage;
    LocalDateTime lastMessageTime;

    @Builder
    public ChildBookDTO(int userId, int childId, String childImageUrl, String childName, String childPhone, String lastMessage, LocalDateTime lastMessageTime) {
        this.userId = userId;
        this.childId = childId;
        this.childImageUrl = childImageUrl;
        this.childName = childName;
        this.childPhone = childPhone;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }
}
