package com.cactus.api.message.CactusMessageApi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
public class Message {
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "contents")
    private String contents;

    @Column(name = "received_user_id")
    private Long receivedUserId;

    @Column(name = "send_user_id")
    private Long sendUserId;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    @Column(name = "is_like")
    private String isLike;

    public Message() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public Message(Long messageId, String contents, Long receivedUserId, Long sendUserId, LocalDateTime insertTime, String isLike) {
        this.messageId = messageId;
        this.contents = contents;
        this.receivedUserId = receivedUserId;
        this.sendUserId = sendUserId;
        this.insertTime = insertTime;
        this.isLike = isLike;
    }
}
