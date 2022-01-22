package com.cactus.server.Zuul.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GLOBAL(2),
    EMPTYTOKEN(9), //토큰없음
    AUTHENTICATION(10), // 인증에러
    JWT_TOKEN_EXPIRED(11); //토큰에러

    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}