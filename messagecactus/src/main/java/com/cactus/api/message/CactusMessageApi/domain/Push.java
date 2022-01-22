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
public class Push {
    @Id
    @Column(name = "push_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long pushId;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_token")
    private String userToken;

    @Column(name = "send_type")
    @Enumerated(EnumType.STRING)
    private SendType sendType;

    @Column(name = "send_id")
    private Long sendId;

    public Push() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public Push(Long pushId, String title, String contents, LocalDateTime insertTime, Long userId, String userToken, SendType sendType, Long sendId) {
        this.pushId = pushId;
        this.title = title;
        this.contents = contents;
        this.insertTime = insertTime;
        this.userId = userId;
        this.userToken = userToken;
        this.sendType = sendType;
        this.sendId = sendId;
    }
}
