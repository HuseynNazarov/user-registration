package com.company.userregistrationapp.exception;

import lombok.Getter;

@Getter
public class InCorrectPasswordException extends RuntimeException {
    private final int code;
    private final String message;

    public InCorrectPasswordException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static InCorrectPasswordException of(int code, String message) {
        return new InCorrectPasswordException(code,
                message);
    }
}
