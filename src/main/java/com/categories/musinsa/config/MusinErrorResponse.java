package com.categories.musinsa.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusinErrorResponse {

    private int code = -1;
    private String message;
    private String errorCode;

    private MusinErrorResponse() {}

    public static MusinErrorResponse systemError (String errorCode , String message ) {
        MusinErrorResponse musinErrorResponse = new MusinErrorResponse();
        musinErrorResponse.code = -2;
        musinErrorResponse.errorCode = errorCode;
        musinErrorResponse.message = message;
        return musinErrorResponse;
    }

    public static MusinErrorResponse error (String errorCode , String message ) {
        MusinErrorResponse musinErrorResponse = new MusinErrorResponse();
        musinErrorResponse.code = -1;
        musinErrorResponse.errorCode = errorCode;
        musinErrorResponse.message = message;
        return musinErrorResponse;
    }
}
