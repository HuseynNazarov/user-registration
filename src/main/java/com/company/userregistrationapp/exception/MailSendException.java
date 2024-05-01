package com.company.userregistrationapp.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailSendException extends RuntimeException {
    int code;
    String message;

    public static MailSendException of(int code, String message) {
        return new MailSendException(code,
                message);
    }
}
