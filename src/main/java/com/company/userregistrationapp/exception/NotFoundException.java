package com.company.userregistrationapp.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private final int code;
    private final String message;

    public NotFoundException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static NotFoundException of(int code, String message) {
        return new NotFoundException(code,
                message);
    }
}
