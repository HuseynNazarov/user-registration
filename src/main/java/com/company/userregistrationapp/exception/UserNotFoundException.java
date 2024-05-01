package com.company.userregistrationapp.exception;

import com.company.userregistrationapp.dto.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults
public class UserNotFoundException extends RuntimeException {
    int code;
    String message;

    public static UserNotFoundException of(ExceptionEnum exceptionEnum) {
        return new UserNotFoundException(exceptionEnum.getCode(),
                exceptionEnum.getMessage());
    }
}
