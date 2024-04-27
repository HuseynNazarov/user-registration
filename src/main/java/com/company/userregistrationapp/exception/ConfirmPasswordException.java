package com.company.userregistrationapp.exception;

import lombok.Getter;

@Getter
public class ConfirmPasswordException extends RuntimeException{
    private final int code;
    private final String message;

    public ConfirmPasswordException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ConfirmPasswordException of(int code, String message) {
        return new ConfirmPasswordException(code,
                message);
    }
}
