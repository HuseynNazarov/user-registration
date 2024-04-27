package com.company.userregistrationapp.exception;

import lombok.Getter;

@Getter
public class UserExistException extends RuntimeException{
    private final int code;
    private final String message;

    public UserExistException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserExistException of(int code, String message) {
        return new UserExistException(code,
                message);
    }
}
