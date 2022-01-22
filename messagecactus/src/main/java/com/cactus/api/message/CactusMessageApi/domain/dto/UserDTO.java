package com.cactus.api.message.CactusMessageApi.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * CactusMessageApi
 * Class: UserDTO
 * Created by hapo on 2019-11-28.
 * Description:
 */

@Getter
@Setter
public class UserDTO {

    private String name;
    private String pushToken;
    private String profileUrl;

    public UserDTO() {
    }

    @Builder
    public UserDTO(String name, String pushToken, String profileUrl) {
        this.name = name;
        this.pushToken = pushToken;
        this.profileUrl = profileUrl;
    }
}
