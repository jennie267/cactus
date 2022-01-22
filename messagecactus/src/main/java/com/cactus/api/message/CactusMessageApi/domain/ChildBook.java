package com.cactus.api.message.CactusMessageApi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
public class ChildBook {
    private Long userId;
    private Long childId;
    private String childImageUrl;
    private String childName;
    private String childPhone;
    private String lastMessage;
    private Date lastMessageTime;

    public ChildBook() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public ChildBook(Long userId, Long childId, String childImageUrl, String childName,String childPhone, String lastMessage, Date lastMessageTime) {
        this.userId = userId;
        this.childId = childId;
        this.childImageUrl = childImageUrl;
        this.childName = childName;
        this.childPhone = childPhone;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }
}
