package com.company.userregistrationapp.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final int code;
    private final String message;

    public UserNotFoundException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserNotFoundException of(int code, String message) {
        return new UserNotFoundException(code,
                message);
    }
}
