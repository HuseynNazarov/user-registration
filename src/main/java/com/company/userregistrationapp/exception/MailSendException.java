package com.company.userregistrationapp.exception;

import lombok.Getter;

@Getter
public class MailSendException extends RuntimeException {
    private final int code;
    private final String message;

    public MailSendException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MailSendException of(int code, String message) {
        return new MailSendException(code,
                message);
    }
}
