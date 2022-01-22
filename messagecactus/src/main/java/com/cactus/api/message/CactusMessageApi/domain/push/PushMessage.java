package com.cactus.api.message.CactusMessageApi.domain.push;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushMessage {
    private Notification notification;
    private EtcData data;
    private String priority = "";
    private String to = "";

    @Builder
    public PushMessage(Notification notification, EtcData data, String priority, String to) {
        this.notification = notification;
        this.data = data;
        this.priority = priority;
        this.to = to;
    }
}
