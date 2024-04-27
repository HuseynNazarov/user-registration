package com.company.userregistrationapp.exception;

import lombok.Getter;

@Getter
public class UserDisabledException extends RuntimeException{
    private final int code;
    private final String message;

    public UserDisabledException(int code, String message) {
        this.code=code;
        this.message=message;
    }

    public static UserDisabledException of(int code, String message) {
        return new UserDisabledException(code,
                message);
    }
}
