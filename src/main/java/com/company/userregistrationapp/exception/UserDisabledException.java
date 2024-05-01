package com.company.userregistrationapp.exception;

import com.company.userregistrationapp.dto.enums.ExceptionEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDisabledException extends RuntimeException {
    int code;
    String message;

    public static UserDisabledException of(ExceptionEnum exceptionEnum) {
        return new UserDisabledException(exceptionEnum.getCode(),
                exceptionEnum.getMessage());
    }
}
