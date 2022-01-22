package com.cactus.api.message.CactusMessageApi.domain.push;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EtcData {
    private String title = "";
    private String body = "";
    private String click_action = "";

    @Builder
    public EtcData(String title, String body, String click_action) {
        this.title = title;
        this.body = body;
        this.click_action = click_action;
    }
}
