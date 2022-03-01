package com.categories.musinsa.config.exception;

import lombok.Getter;

import java.util.Map;

@SuppressWarnings("serial")
@Getter
public class MusinException extends RuntimeException {

    private final MusinCodeEnum code;
    private Map<String,String> args;

    public MusinException(MusinCodeEnum code) {
        super(code.getName() + "/" + code.getMessage());
        this.code = code;
    }

    public MusinException(MusinCodeEnum code , Map<String,String> args) {
        super(code.getName() + "/" + code.getMessage() + "/" + args);
        this.code = code;
        this.args = args;
    }

    public MusinException(MusinCodeEnum code , String message) {
        super(message);
        this.code = code;
    }

    public MusinException(MusinCodeEnum code , Map<String,String> args , String message) {
        super(message);
        this.code = code;
        this.args = args;
    }
}
