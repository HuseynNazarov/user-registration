package com.company.userregistrationapp.exception;

import com.company.userregistrationapp.dto.enums.ExceptionEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConfirmPasswordException extends RuntimeException {
    int code;
    String message;

    public static ConfirmPasswordException of(ExceptionEnum exceptionEnum) {
        return new ConfirmPasswordException(exceptionEnum.getCode(),
                exceptionEnum.getMessage());
    }
}
